/*
 * @author: Jimmy Starling.
 * Dynamic question page.
 * 
 */

import 'package:flutter/material.dart';
import '../constants.dart';


class Question1 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: 
          Container(
            padding: EdgeInsets.only(top: 40.0),
            child: 
            Column(
              children: <Widget>[
                ElevatedButton(
                  onPressed: () {
                    Navigator.pop(context);
                  },
                  child: Text('Go back!'),
                ),
              ]
            ),
          ),
      ),
    );
  }
}