import 'package:flutter/foundation.dart';

import 'expertise.dart';

class QuestionModel extends ChangeNotifier {
  /// The private field backing [expertise].
  ExpertiseModel _expertise;

  /// Internal, private state of the questions. Stores the ids of each item.
  final List<int> _itemIds = [];

  /// The current expertise. Used to construct items from numeric ids.
  ExpertiseModel get expertise => _expertise;

  set expertise(ExpertiseModel newExpertise) {
    _expertise = newExpertise;
    // Notify listeners, in case the new expertise provides information
    // different from the previous one. For example, availability of an item
    // might have changed.
    notifyListeners();
  }

  /// List of items in the questions.
  List<QuestionItem> get items => _itemIds.map((id) => _expertise.getById(id)).toList();

  /// The current total price of all items.
  int get totalPrice =>
      items.fold(0, (total, current) => total + current.price);

  /// Adds [item] to questions. This is the only way to modify the questions from outside.
  void add(QuestionItem item) {
    _itemIds.add(item.id);
    // This line tells [Model] that it should rebuild the widgets that
    // depend on it.
    notifyListeners();
  }

  void remove(QuestionItem item) {
    _itemIds.remove(item.id);
    // Don't forget to tell dependent widgets to rebuild _every time_
    // you change the model.
    notifyListeners();
  }
}