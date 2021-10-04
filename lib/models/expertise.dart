// Copyright 2019 The Flutter team. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:flutter/material.dart';

/// A proxy of the catalog of items the user can buy.
///
/// In a real app, this might be backed by a backend and cached on device.
/// In this sample app, the catalog is procedurally generated and infinite.
///
/// For simplicity, the catalog is expected to be immutable (no products are
/// expected to be added, removed or changed during the execution of the app).
class ExpertiseModel {
  static List<String> itemNames = [
    'Enfermagem',
    'Callcenter',
    'Recepcao',
    'Marcacao',
    'Vacina',
    'Laboratorio',
    'Medicos',
    'Farmacia',
    'Limpeza',
    'Geral',
  ];

  /// Get item by [id].
  ///
  /// In this sample, the catalog is infinite, looping over [itemNames].
  QuestionItem getById(int id) => QuestionItem(id, itemNames[id % itemNames.length]);

  /// Get item by its position in the catalog.
  QuestionItem getByPosition(int position) {
    // In this simplified case, an item's position in the catalog
    // is also its id.
    return getById(position);
  }
}

@immutable
class QuestionItem {
  final int id;
  final String question_name;
  final Color color;
  final int price = 42;

  QuestionItem(this.id, this.question_name)
      // To make the sample app look nicer, each item is given one of the
      // Material Design primary colors.
      : color = Colors.primaries[id % Colors.primaries.length];

  @override
  int get hashCode => id;

  @override
  bool operator ==(Object other) => other is QuestionItem && other.id == id;
}