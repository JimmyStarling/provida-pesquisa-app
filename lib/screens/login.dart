/*
 * @author: Jimmy Starling.
 * Login Form with validation.
 * 
 */

import 'package:flutter/material.dart';

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
    return Form(
      key: _formKey,
      child: Column(
        children: <Widget>[
          TextFormField(
            decoration: const InputDecoration(labelText: 'Nome Completo'),
            keyboardType: TextInputType.text,
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Porfavor insira o nome completo';
              }
              return null;
            },
          ),
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
              }
              return null;
            },
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 35.0, horizontal: 35.0),
            child: ElevatedButton(
              onPressed: () {
                if (_formKey.currentState.validate()) {
                  ScaffoldMessenger.of(context)
                      .showSnackBar(SnackBar(content: Text('Iniciando a Pesquisa')));
                }
              },
              child: Text(
                'Iniciar Pesquisa',
                textScaleFactor: 2,
                style: TextStyle(color: Colors.black),
              ),
              style: ButtonStyle(
                shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                  RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(18.0),
                    side: BorderSide(color: Colors.blue[600])
                  )
                )
              ),
            ),
          ),
        ],
      ),
    );
  }
}