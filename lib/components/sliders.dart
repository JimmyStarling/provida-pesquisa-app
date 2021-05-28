import 'package:flutter/material.dart';


class QuestionSlider extends StatefulWidget {
  const QuestionSlider({Key key}) : super(key: key);

  @override
  State<QuestionSlider> createState() => _QuestionSliderState();
}

class _QuestionSliderState extends State<QuestionSlider> {
  double _currentSliderValue = 3;
  String _currentSlideStringValue = "";

  @override
  Widget build(BuildContext context) {
    return Slider(
      value: _currentSliderValue,
      min: 0,
      max: 10,
      divisions: 3,
      label: _currentSlideStringValue,
      onChanged: (double value) {
        setState(() {
          var slideValues = value.toString();
          _currentSliderValue = value;
          if ( slideValues == '10'){
            _currentSlideStringValue = 'otimo';
          } else if ( slideValues == '7'){
            _currentSlideStringValue = 'bom';
          } else if ( slideValues == '4'){
            _currentSlideStringValue = 'regular';
          } else if ( slideValues == '0'){
            _currentSlideStringValue = 'ruim';
          }
        });
      },
    );
  }
}