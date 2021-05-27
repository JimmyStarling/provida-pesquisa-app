import 'package:app_pesquisa_de_satisfacao/screens/questions.dart';
import 'package:flutter/material.dart';


class QuestionSlider extends StatefulWidget {
  const QuestionSlider({Key key}) : super(key: key);

  @override
  State<QuestionSlider> createState() => _QuestionSliderState();
}

class _QuestionSliderState extends State<QuestionSlider> {
  double _currentSliderValue = 3;

  @override
  Widget build(BuildContext context) {
    return Slider(
      value: _currentSliderValue,
      min: 0,
      max: 10,
      divisions: 3,
      label: ( _currentSliderValue == 3 )? (_currentSliderValue+4).round().toString()  : (_currentSliderValue).round().toString(),
      onChanged: (double value) {
        setState(() {
          _currentSliderValue = value;
        });
      },
    );
  }
}