import 'package:app_pesquisa_de_satisfacao/screens/nurse_questions.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:hive/hive.dart';
import 'package:hive_flutter/hive_flutter.dart';

import './screens/login.dart';
import 'models/client.dart';

Future main() async {

  WidgetsFlutterBinding.ensureInitialized();

  await Hive.initFlutter();
  Hive.registerAdapter(ClientAdapter());
  await Hive.openBox<Client>('clients');

  runApp(Root());
} 

class Root extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Provida - Pesquisa de Satisfacao',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: Routes(),
    );
  }
}

class Routes extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      // ignore: missing_return
      onGenerateRoute: (RouteSettings settings) {
        switch (settings.name) {
          case '/':
            return CupertinoPageRoute(
              builder: (_) => LoginPage(), settings: settings);
          case '/nurse/question1':
            return CupertinoPageRoute(
              builder: (_) => NurseQuestionPage(), settings: settings);
          case '/nurse/question2':
            return CupertinoPageRoute(
              builder: (_) => NurseQuestion2(), settings: settings);
        }
      },
    );
  }
}