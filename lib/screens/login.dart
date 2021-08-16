/*
 * @author: Jimmy Starling.
 * Login Form with validation.
 * 
 */

import 'package:app_pesquisa_de_satisfacao/models/client.dart';
import 'package:app_pesquisa_de_satisfacao/services/database.dart';
import 'package:flutter/material.dart';
import 'package:app_pesquisa_de_satisfacao/screens/nurse_questions.dart';
import 'package:hive/hive.dart';
import 'dart:developer';
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

  DatabaseHelper database;

  // Local variables to database
  Map<String, String> _client = {
    'name':'name',
    'questions':'questions',
    'complete':'complete'
  };

  // Visual list of clients registered
  // What occurs if there isn't data
	// List<Widget> get _clientList => _clients.map((client) => format(client)).toList();

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
                        log('D/ The value of variable fullName is ${fullName.toString()}');//[0]+fullName[1]}');
                        _client['name'] = (fullName[0]).toString();
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
                  //ListView( children: _clientList )
                ],
              ),// Column Child
          ])// Column Form
        )// Form 
      )// Padding
    );// Scaffold
  }

	void _save() async {

		Navigator.of(context).pop();
		Client client = Client(
			_client[0].toString(),
      _client[1].toString(),
      DateTime.now(),
			false
		);

    // Save into database
    database.insertClient(client);

    // Console
    log('D/ The client table:${(Client).toString()}'+', the client variable data: ${client.toString()}');
		setState(() => _client = {} );
		refresh();
	}

	@override
	void initState() {
		refresh();
		super.initState();
	}

	void refresh() async {

    var databox = await Hive.openBox('clientBox');
    // Inserting fake data
    var kafka = Client(
      'Kafka',
      'questions:{question1:{"Paths are made by walking?":true}}',
      DateTime.now(),
      true,
    );
    databox.add(kafka);
    final clients =  databox.getAt(0);
    log('D/ clients data from databox is ${clients.toString()}');
    // Return a list of clients into database converting into Map<String, dynamic> = 
		// List<Map<String, dynamic>> _results = (await database.getClients()).cast<Map<String, dynamic>>();
    var _results = await database.getClients();
    log('Client table data: ${_results.toString()}');
		//_client = _results.map((client) => Client.fromMap(client)).toList() as Map<String, String>;
		setState(() { });
	}
}