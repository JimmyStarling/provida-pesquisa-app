/*
 * @author: Jimmy Starling.
 * Login Form with validation.
 * 
 */

import 'dart:html';

import 'package:app_pesquisa_de_satisfacao/models/client.dart';
import 'package:app_pesquisa_de_satisfacao/services/database.dart';
import 'package:flutter/material.dart';
<<<<<<< HEAD
import 'package:app_pesquisa_de_satisfacao/screens/questions/nurse_questions.dart';
=======
import 'package:app_pesquisa_de_satisfacao/screens/nurse_questions.dart';
>>>>>>> 280f87b0d03843ed235dbb6d9c6f5fc6d1f7e9ef
import 'package:hive_flutter/hive_flutter.dart';
import 'dart:developer';
import 'package:flutter/foundation.dart';
import '../boxes.dart';
import '../constants.dart';

// Define a custom Form widget.
class LoginPage extends StatefulWidget {
  @override
  LoginPageState createState() {
    return LoginPageState();
  }
}

class LoginPageState extends State<LoginPage> {
  @override void dispose(){
    Hive.box('client').close();
    super.dispose();
  }

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
                        if (_formKey.currentState.validate()){ 
                          _save();
                          Navigator.pushNamed(context, '/nurse/question');
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
                            MaterialPageRoute(builder: (context) => NurseQuestionPage()),
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

  Future addClient(String name, String questions) async{
    final client = Client()
      ..name = name
      ..questions = questions
      ..completed = false
      ..createdDate = DateTime.now();

    final box = Boxes.getClients();
    box.add(client);
  }

	void _save() async {

		Navigator.of(context).pop();
    
		setState(() => _client = {} );
		refresh();
	}

	@override
	void initState() {
		refresh();
		super.initState();
	}

	void refresh() async {
    initState();
<<<<<<< HEAD
    /* Inserting fake data
    var kafka = Client()
      ..name = 'Kafka'
      ..questions = 'questions:{question:{"Paths are made by walking?":true}}'
      ..created = DateTime.now()
      ..completed = true;
      
    dataBox.add(kafka);*/

    // Return a list of clients into database converting into Map<String, dynamic> = 
    //var _results = dataBox.get('kafka');//database.getClients();
    //debugPrint('Client table data: ${_results.toString()}');
		//_client = _results.map((client) => Client.fromMap(client)).toList() as Map<String, String>;
		//setState(() { });
=======
>>>>>>> 280f87b0d03843ed235dbb6d9c6f5fc6d1f7e9ef
	}
}