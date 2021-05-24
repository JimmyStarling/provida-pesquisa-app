/*
 * @author: Jimmy Starling.
 * Login Form with validation.
 * 
 */

import 'package:flutter/material.dart';
import 'package:app_pesquisa_de_satisfacao/screens/questions.dart';
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

  bool isEmailOrNumber;

  @override
  Widget build(BuildContext context) {
    // Build a Form widget using the _formKey created above.
    return 
    Scaffold(
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
                      if (value == null || value.isEmpty) {
                        return 'Porfavor insira o nome completo';
                      }
                      return null;
                    },
                  ),// Name Field
                  TextFormField(
                    decoration: const InputDecoration(labelText: 'Contato'),
                    keyboardType: inputType,
                    // Validating the text input
                    validator: (value) {
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
                          Navigator.push(
                            context,
                            MaterialPageRoute(builder: (context) => Question1()),
                          );
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
                  )
                ],
              ),// Column Child
          ])// Column Form
        )// Form 
      )// Padding
    );// Scaffold
  }
}