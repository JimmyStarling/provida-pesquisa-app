import 'dart:async';
import 'dart:developer';
import 'package:app_pesquisa_de_satisfacao/models/client.dart';
import 'package:flutter/widgets.dart';
import 'package:hive/hive.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:idb_sqflite/idb_sqflite.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
import 'package:idb_sqflite/idb_sqflite.dart' as mDatabase; 
abstract class DatabaseHelper {

  static var databox;

	static int get _version => 1;

  static mDatabase.Database _database;
  static final _databaseName = "banco_pesquisa.db";


  static var store;
  static var key;

  static final table = 'clients';

  static final columnId = '_id';
  static final columnName = 'name';
  static final columnQuestions = 'questions';
  static final columnComplete = 'complete';

  Future<void> initDatabase () async {
    // Registering the client adapter
    Hive.registerAdapter(ClientAdapter());
    // Initializing Flutter
    await Hive.initFlutter();
    // Open database
    databox = await Hive.openBox('clientBox');
    // Inserting fake data
    var kafka = Client()
      ..name = 'Kafka'
      ..questions = 'questions:{question1:{"Paths are made by walking?":true}}'
      ..createdDate = DateTime.now()
      ..completed = true
    ;
    await insertClient(kafka);
    print(getClients());
  }
  // Define a function that inserts clients into the database
  Future<void> insertClient(Client client) async {
    databox.add(client);
  }

  // A method that retrieves all the clients from the clients table.
  Future<void> getClients() async {
    initDatabase();
    // Getting transactioned client as List
    final clients =  databox;//.getAt(0); //as List<Map<String, dynamic>>;
    log('D/ clients data from databox is ${clients.toString()}');
    return clients;
  }

}