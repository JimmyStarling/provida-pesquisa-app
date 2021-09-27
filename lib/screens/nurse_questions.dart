/*
 * @author: Jimmy Starling.
 * Dynamic question page.
 * 
 */

import 'package:app_pesquisa_de_satisfacao/models/client.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:hive/hive.dart';

import '../boxes.dart';
import '../components/sliders.dart';

<<<<<<< HEAD
=======
import '../models/user.dart';

class NurseQuestion1 extends StatelessWidget {
  final User user;
  NurseQuestion1({ Key key, @required this.user }): super(key: key);

  final _formQuestionKey = GlobalKey<FormState>();
>>>>>>> 99b48a470a14e5f7edc974b4bb2e46c5035fcc9d

void editClientQuestion(
    Client client,
    String name,
    Object questions,
  ) {
    client.name = name;
    client.questions = questions.toString();
    client.completed = false;

    client.save();
  }

void deleteClient(Client client) {
  client.delete();
}


class NurseQuestionPage extends StatefulWidget {
  @override
  _NurseQuestionPageState createState() => _NurseQuestionPageState();
}

class _NurseQuestionPageState extends State<NurseQuestionPage> {
  
  @override
  void dispose() {
    Hive.close();

    super.dispose();
  }

  Widget build(BuildContext context) => Scaffold(
      body: ValueListenableBuilder<Box<Client>>(
          valueListenable: Boxes.getClients().listenable(),
          builder: (context, box, _) {
            final clients = box.values.toList().cast<Client>();

            return buildContent(context, clients, 1);
          }
      ),
  );

  Widget buildContent(BuildContext context, List<Client> clients, var question){
    final _formQuestionKey = GlobalKey<FormState>();
    if (question == 1) {
      
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text("Realizando Novo Cadastro!"),
      ));
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
                        'Em uma escala de 0 à 10\n'+
                        'Qual sua satisfação com o atendimento da\n'+
                        'EQUIPE DE ENFERMAGEM\n',
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
                    buttonBuilder(_formQuestionKey, context, client),
                  ]
              ),
            ), 
          ),
        ),
      );
    } else if (question == 2){
      final _formQuestion2Key = GlobalKey<FormState>();
      return Scaffold(
      body: Center(
        child: 
          Container(
            padding: EdgeInsets.only(top: 40.0),
            child:
            Form(
              key: _formQuestion2Key,
              child:
              Column(
                children: <Widget>[
                  Image.asset(
                    'images/provida_logomarca.png',
                  ),
                  Container(  
                    margin: const EdgeInsets.only(top: 20.0),
                    child: Text(
                      'Em uma escala de 0 à 10\nQual sua avaliação geral quanto ao\nAGILIDADE NO ATENDIMENTO DA EQUIPE DE ENFERMAGEM\n',
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
                          if (_formQuestion2Key.currentState.validate()) {
                            Navigator.pushNamed(context, '/infrastructure/question1');
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
                          Navigator.popUntil(context, ModalRoute.withName('/nurse/question1'));
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
    } else if(question == 3){
      final _formQuestion2Key = GlobalKey<FormState>();
      return Scaffold(
      body: Center(
        child: 
          Container(
            padding: EdgeInsets.only(top: 40.0),
            child:
            Form(
              key: _formQuestion2Key,
              child:
              Column(
                children: <Widget>[
                  Image.asset(
                    'images/provida_logomarca.png',
                  ),
                  Container(  
                    margin: const EdgeInsets.only(top: 20.0),
                    child: Text(
                      'Em uma escala de 0 à 10\nQual sua avaliação geral quanto ao\nAGILIDADE NO ATENDIMENTO DA EQUIPE DE ENFERMAGEM\n',
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
                          if (_formQuestion2Key.currentState.validate()) {
                            Navigator.pushNamed(context, '/infrastructure/question1');
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
                          Navigator.popUntil(context, ModalRoute.withName('/nurse/question1'));
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

  Widget buttonBuilder(GlobalKey<FormState> _formQuestionKey, BuildContext context, Client clients, var question) => Column(
    children: [
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
              editClientQuestion(clients, "", {"question1":"test"});
              //Navigator.pushNamed(context, '/nurse/question2');
              ScaffoldMessenger.of(context)
                  .showSnackBar(SnackBar(content: Text('Agora vamos falar sobre a estrutura')));
                  
              buildContent();
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
    ],
  );
}




class NurseQuestion2 extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    }

}
