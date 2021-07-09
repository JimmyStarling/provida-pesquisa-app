import 'dart:async';
import 'dart:developer';
import 'package:app_pesquisa_de_satisfacao/models/client.dart';
import 'package:flutter/widgets.dart';
import 'package:hive/hive.dart';
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

  Future<mDatabase.Database> get database async {
    if (_database != null)
    return _database;

    // if _database is null we instantiate it
    _database = await initDatabase();
    return _database;
  }

  initDatabase () async {
    //var factory = getIdbFactorySqflite(databaseFactory);

    //log('D/ Init the database.');
    //WidgetsFlutterBinding.ensureInitialized();
    
    // Open database
    databox = await Hive.openBox('clientBox');
    // Inserting fake data
    var kafka = Client(
      'Kafka',
      'questions:{question1:{"Paths are made by walking?":true}}',
      DateTime.now(),
      true,
    );
    await insertClient(kafka);
    print(await getClients());
  }
  // Define a function that inserts clients into the database
  Future<void> insertClient(Client client) async {
    databox.add(client);
  }

  // A method that retrieves all the clients from the clients table.
  getClients() async {
    initDatabase();
    // Getting transactioned client as List
    final clients =  databox.getAt(0); //as List<Map<String, dynamic>>;
    log('D/ clients data from databox is ${clients.toString()}');
    return clients;
    /* It returns a object and convert it to maps
    final List<Map<String, dynamic>> maps = clients;
    // Convert the List<Map<String, dynamic> into a List<Client>.
    return List.generate(maps.length, (i) {
      return Client(
        maps[i]['id'],
        maps[i]['name'],
        maps[i]['questions'],
        maps[i]['complete'],
      );
    });*/
  }

}