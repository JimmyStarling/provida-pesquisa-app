/*
 * @author: Jimmy Starling.
 * Dynamic question page.
 * 
 */

import 'package:app_pesquisa_de_satisfacao/screens/login.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import '../constants.dart';

import '../components/sliders.dart';
import '../utils/slideAnimation.dart';

class Question1 extends StatelessWidget {
  final _formQuestionKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: 
          Container(
            padding: EdgeInsets.only(top: 40.0),
            child:
            Form(
              key: _formQuestionKey,
              child:
              Column(
                children: <Widget>[
                  Image.asset(
                    'images/provida_logomarca.png',
                  ),
                  Container(  
                    margin: const EdgeInsets.only(top: 20.0),
                    child: Text(
                      'Em uma escala de 0 à 10\nQual sua avaliação geral quanto ao\nATENDIMENTO DAS EQUIPES\n',
                      textAlign: TextAlign.center,
                      overflow: TextOverflow.ellipsis,
                      style: const TextStyle(
                        fontSize: 20.0,
                        fontWeight: FontWeight.normal,
                        fontFamily: 'Questrial'
                      ),
                    ),
                  ),
                  Image.asset(
                    'images/enfermagem.png',
                  ),
                  QuestionSlider(),
                  Container(
                      margin: const EdgeInsets.only(top: 40.0, left: 20.0, right: 20.0),
                      width: double.infinity,
                      height: 40,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(20),
                      ),
                      child: TextButton(
                        onPressed: () {
                          if (_formQuestionKey.currentState.validate()) {
                            Navigator.pushNamed(context, '/question/2');// Go to question 2

                            ScaffoldMessenger.of(context)
                                .showSnackBar(SnackBar(content: Text('Agora vamos falar sobre a estrutura')));
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
                          'Continuar',
                          style: TextStyle(color: Colors.white),
                        ),
                      ),
                  ),
                  Container(
                      margin: const EdgeInsets.only(top: 20.0, left: 20.0, right: 20.0),
                      width: double.infinity,
                      height: 40,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(20),
                      ),
                      child: TextButton(
                        onPressed: () {
                          Navigator.popUntil(context, ModalRoute.withName('/'));// Go back to login
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
                          'Voltar',
                          style: TextStyle(color: Colors.indigo[900]),
                        ),
                      ),
                    )
                ]
              ),
            ), 
          ),
      ),
    );
  }
}

class Question2 extends StatelessWidget {
  final _formQuestionKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: 
          Container(
            padding: EdgeInsets.only(top: 40.0),
            child:
            Form(
              key: _formQuestionKey,
              child:
              Column(
                children: <Widget>[
                  Image.asset(
                    'images/provida_logomarca.png',
                  ),
                  Container(  
                    margin: const EdgeInsets.only(top: 20.0),
                    child: Text(
                      'Em uma escala de 0 à 10\nQual sua avaliação geral quanto ao\nAGILIDADE NO ATENDIMENTO DAS EQUIPES\n',
                      textAlign: TextAlign.center,
                      overflow: TextOverflow.ellipsis,
                      style: const TextStyle(
                        fontSize: 20.0,
                        fontWeight: FontWeight.normal,
                        fontFamily: 'Questrial'
                      ),
                    ),
                  ),
                  Image.asset(
                    'images/enfermagem.png',
                  ),
                  QuestionSlider(),
                  Container(
                      margin: const EdgeInsets.only(top: 40.0, left: 20.0, right: 20.0),
                      width: double.infinity,
                      height: 40,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(20),
                      ),
                      child: TextButton(
                        onPressed: () {
                          if (_formQuestionKey.currentState.validate()) {
                            Navigator.push(context, EnterExitRoute(exitPage: this, enterPage: Question3()));
                            ScaffoldMessenger.of(context)
                                .showSnackBar(SnackBar(content: Text('Agora vamos falar sobre a estrutura')));
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
                          'Continuar',
                          style: TextStyle(color: Colors.white),
                        ),
                      ),
                  ),
                  Container(
                      margin: const EdgeInsets.only(top: 20.0, left: 20.0, right: 20.0),
                      width: double.infinity,
                      height: 40,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(20),
                      ),
                      child: TextButton(
                        onPressed: () {
                          Navigator.popUntil(context, ModalRoute.withName('/question/1'));
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
                          'Voltar',
                          style: TextStyle(color: Colors.indigo[900]),
                        ),
                      ),
                    )
                ]
              ),
            ), 
          ),
      ),
    );
  }
}

class Question3 extends StatelessWidget {
  final _formQuestionKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: 
          Container(
            padding: EdgeInsets.only(top: 40.0),
            child:
            Form(
              key: _formQuestionKey,
              child:
              Column(
                children: <Widget>[
                  Image.asset(
                    'images/provida_logomarca.png',
                  ),
                  Container(  
                    margin: const EdgeInsets.only(top: 20.0),
                    child: Text(
                      'Em uma escala de 0 à 10\nQual sua avaliação geral quanto à\nESTRUTURA DO MULTICENTRO\n',
                      textAlign: TextAlign.center,
                      overflow: TextOverflow.ellipsis,
                      style: const TextStyle(
                        fontSize: 20.0,
                        fontWeight: FontWeight.normal,
                        fontFamily: 'Questrial'
                      ),
                    ),
                  ),
                  Image.asset(
                    'images/enfermagem.png',
                  ),
                  QuestionSlider(),
                  Container(
                      margin: const EdgeInsets.only(top: 40.0, left: 20.0, right: 20.0),
                      width: double.infinity,
                      height: 40,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(20),
                      ),
                      child: TextButton(
                        onPressed: () {
                          if (_formQuestionKey.currentState.validate()) {
                            Navigator.pushNamed(context, '/question/4');
                            ScaffoldMessenger.of(context)
                                .showSnackBar(SnackBar(content: Text('Agora vamos falar sobre a estrutura')));
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
                          'Continuar',
                          style: TextStyle(color: Colors.white),
                        ),
                      ),
                  ),
                  Container(
                      margin: const EdgeInsets.only(top: 20.0, left: 20.0, right: 20.0),
                      width: double.infinity,
                      height: 40,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(20),
                      ),
                      child: TextButton(
                        onPressed: () {
                          Navigator.popUntil(context, ModalRoute.withName('/question/2'));
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
                          'Voltar',
                          style: TextStyle(color: Colors.indigo[900]),
                        ),
                      ),
                    )
                ]
              ),
            ), 
          ),
      ),
    );
  }
}

class Question4 extends StatelessWidget {
  final _formQuestionKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: 
          Container(
            padding: EdgeInsets.only(top: 40.0),
            child:
            Form(
              key: _formQuestionKey,
              child:
              Column(
                children: <Widget>[
                  Image.asset(
                    'images/provida_logomarca.png',
                  ),
                  Container(  
                    margin: const EdgeInsets.only(top: 20.0),
                    child: Text(
                      'Em uma escala de 0 à 10\nQual sua avaliação geral quanto à\nESTRUTURA DO MULTICENTRO\n',
                      textAlign: TextAlign.center,
                      overflow: TextOverflow.ellipsis,
                      style: const TextStyle(
                        fontSize: 20.0,
                        fontWeight: FontWeight.normal,
                        fontFamily: 'Questrial'
                      ),
                    ),
                  ),
                  Image.asset(
                    'images/enfermagem.png',
                  ),
                  QuestionSlider(),
                  Container(
                      margin: const EdgeInsets.only(top: 40.0, left: 20.0, right: 20.0),
                      width: double.infinity,
                      height: 40,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(20),
                      ),
                      child: TextButton(
                        onPressed: () {
                          if (_formQuestionKey.currentState.validate()) {
                            Navigator.pushNamed(context, '/question/5');
                            ScaffoldMessenger.of(context)
                                .showSnackBar(SnackBar(content: Text('Agora vamos falar sobre a estrutura')));
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
                          'Continuar',
                          style: TextStyle(color: Colors.white),
                        ),
                      ),
                  ),
                  Container(
                      margin: const EdgeInsets.only(top: 20.0, left: 20.0, right: 20.0),
                      width: double.infinity,
                      height: 40,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(20),
                      ),
                      child: TextButton(
                        onPressed: () {
                          Navigator.popUntil(context, ModalRoute.withName('/question/3'));
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
                          'Voltar',
                          style: TextStyle(color: Colors.indigo[900]),
                        ),
                      ),
                    )
                ]
              ),
            ), 
          ),
      ),
    );
  }
}