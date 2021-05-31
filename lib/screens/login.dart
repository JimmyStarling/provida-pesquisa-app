/*
 * @author: Jimmy Starling.
 * Login Form with validation.
 * 
 */

import 'package:app_pesquisa_de_satisfacao/models/client_model.dart';
import 'package:app_pesquisa_de_satisfacao/services/database.dart';
import 'package:flutter/material.dart';
import 'package:app_pesquisa_de_satisfacao/screens/nurse_questions.dart';
import '../constants.dart';

// Define a custom Form widget.
class LoginPage extends StatefulWidget {
  @override
  LoginPageState createState() {
    return LoginPageState();
  }
}

class LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  var inputType = TextInputType.text;
  var onlyNumber = RegExp(r'^[0-9]+$');
  var onlyEmail = RegExp(r'^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$');

  // Local variables to database
  Map<String, String> _client = {
    'name':'name',
    'surname':'surname'
  };
	List<Client> _clients = [];
  List<Client> _fakeClients = [];

  // Visual list of clients registered
  // What occurs if there isn't data
	List<Widget> get _clientList => 
    (_clients != null)? 
      _clients.map((client) => format(client)).toList(): _fakeClients.map((client) => format(client)).toList();
	TextStyle _style = TextStyle(color: Colors.white, fontSize: 24);

  bool isEmailOrNumber;

  @override
  Widget build(BuildContext context) {
    // Build a Form widget using the _formKey created above.
    return Scaffold(
      body: Padding(padding: EdgeInsets.all(50),
      child: 
        Form(
          key: _formKey,
          child: Column(  
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[  
              Column(
                children: <Widget>[
                  Image.asset(
                    'images/provida_logomarca.png',
                  ),
                  Padding(padding: EdgeInsets.only(top: 40.0)),
                  TextFormField(
                    decoration: const InputDecoration(labelText: 'Nome Completo'),
                    keyboardType: TextInputType.text,
                    validator: (value) {
                      // Split value in two
                      var fullName = value.split(" ");

                      if (value == null || value.isEmpty) {
                        return 'Porfavor insira o nome completo';
                      } else {
                        _client['name'] = fullName[0].toString();
                        _client['surname'] = fullName[1].toString();
                      }
                      return null;
                    },
                  ),// Name Field
                  TextFormField(
                    decoration: const InputDecoration(labelText: 'Contato'),
                    keyboardType: inputType,
                    // Validating the text input
                    validator: (value) {
                      // Get readable string
                      var contactAdress = value.toString();

                      if (value == null || value.isEmpty) {
                        return 'Porfavor insira o numero ou endereço de email válido.';
                      } else if (onlyEmail.hasMatch(value)) {
                        inputType = TextInputType.emailAddress;
                      } else if (onlyNumber.hasMatch(value)) {
                        inputType = TextInputType.phone;
                      } else if (!onlyEmail.hasMatch(value) || !onlyNumber.hasMatch(value)){
                        return 'Porfavor insira o numero ou endereço de email válido.';
                      }
                      return null;
                    },
                  ),// Contact Field
                  Container(
                    margin: const EdgeInsets.only(top: 40.0),
                    width: double.infinity,
                    height: 40,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(20),
                    ),
                    child: TextButton(
                      onPressed: () {
                        if (_formKey.currentState.validate()) {
                          // Saving all local informations to the database
                          _save();
                          Navigator.pushNamed(context, '/nurse/question1');
                          ScaffoldMessenger.of(context)
                              .showSnackBar(SnackBar(content: Text('Iniciando a Pesquisa')));
                        }
                      },
                      style: ButtonStyle(
                        backgroundColor: MaterialStateProperty.all(Colors.indigo[900]),
                        shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                          RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(18.0),
                            side: BorderSide(color: Colors.indigo[900]),
                          )
                        )
                      ),
                      child: Text(
                        'Iniciar Pesquisa',
                        style: TextStyle(color: Colors.white),
                      ),
                    ),
                  ),
                  Container(
                    margin: const EdgeInsets.only(top: 40.0),
                    width: double.infinity,
                    height: 40,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(20),
                    ),
                    child: TextButton(
                      onPressed: () {
                        if (_formKey.currentState.validate()) {
                          Navigator.push(
                            context,
                            MaterialPageRoute(builder: (context) => NurseQuestion1()),
                          );
                          ScaffoldMessenger.of(context)
                              .showSnackBar(SnackBar(content: Text('Iniciando a Pesquisa')));
                        }
                      },
                      style: ButtonStyle(
                        shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                          RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(18.0),
                            side: BorderSide(color: Colors.indigo[900]),
                          )
                        )
                      ),
                      child: Text(
                        'Carregar Pesquisa',
                        style: TextStyle(color: Colors.indigo[900]),
                      ),
                    ),
                  ),
                  TextButton(
                    onPressed: (){
                      _client['name'] = 'testing';
                      _client['surname'] = 'testing2';
                      _save();
                    },
                    child: Text(
                      'Insert Fake Data',
                      style: TextStyle(color: Colors.indigo[900]),
                    ),
                  ),
                  ListView( children: _clientList )
                ],
              ),// Column Child
          ])// Column Form
        )// Form 
      )// Padding
    );// Scaffold
  }
  Widget format(Client client) {
    return Dismissible(
          key: Key(client.id.toString()),
          child: Padding(
            padding: EdgeInsets.fromLTRB(12, 6, 12, 4),
            child: TextButton(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  Text(client.name, style: _style),
                  Text(client.surname, style: _style),
                  Icon(client.complete == true ? Icons.radio_button_checked : Icons.radio_button_unchecked, color: Colors.white)
                ]
              ),
              onPressed: () => _completed(client),
            )
          ),
          onDismissed: (DismissDirection direction) => _delete(client),
        );
  }

  void _completed(Client client) async {

		client.complete = !client.complete;
		dynamic result = await DB.update(Client.table, client);
		print(result);
		refresh();
	}


  void _delete(Client client) async {
		
		DB.delete(Client.table, client);
		refresh();
	}

	void _save() async {

		Navigator.of(context).pop();
		Client client = Client(
			name: _client[0],
      surname: _client[1],
			complete: false
		);

    // Inserting and refreshing the cache
		await DB.insert(Client.table, client);
		setState(() => _client = {} );
		refresh();
	}

	@override
	void initState() {

    _client['name'] = 'Jimmy';
    _client['surname'] = 'Starling';
    _save();

		refresh();
		super.initState();
	}

	void refresh() async {

		List<Map<String, dynamic>> _results = await DB.query(Client.table);
		_clients = _results.map((client) => Client.fromMap(client)).toList();
		setState(() { });
	}
}