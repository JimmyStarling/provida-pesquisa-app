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
      home: LoginPage(),
    );
  }
}
