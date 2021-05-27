import 'package:app_pesquisa_de_satisfacao/screens/questions.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import './screens/login.dart';

void main() => runApp(Root());

class Root extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Provida - Pesquisa de Satisfacao',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: Main(),
    );
  }
}

class Main extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      // ignore: missing_return
      onGenerateRoute: (RouteSettings settings) {
        switch (settings.name) {
          case '/':
            return CupertinoPageRoute(
              builder: (_) => LoginPage(), settings: settings);
          case '/question/1':
            return CupertinoPageRoute(
              builder: (_) => Question1(), settings: settings);
          case '/question/2':
            return CupertinoPageRoute(
              builder: (_) => Question2(), settings: settings);
          case '/question/3':
            return CupertinoPageRoute(
              builder: (_) => Question3(), settings: settings);
          case '/question/4':
            return CupertinoPageRoute(
              builder: (_) => Question4(), settings: settings);
        }
      },
    );
  }
}