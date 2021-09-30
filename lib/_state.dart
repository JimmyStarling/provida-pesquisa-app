import 'package:app_pesquisa_de_satisfacao/item.dart';
import 'package:flutter/foundation.dart';
import 'package:provida-pesquisa-app/item.dart';

class AppState with ChangeNotifier {
  List<Question> _items = sampleQuestions;

  List<Question> get items => _items;

  void addItem(Question item) {
    _items.add(item);

    notifyListeners();
  }
}