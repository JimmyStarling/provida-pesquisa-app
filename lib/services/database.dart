import 'dart:async';
import 'dart:developer';
import 'package:app_pesquisa_de_satisfacao/models/client.dart';
import 'package:flutter/widgets.dart';
import 'package:idb_sqflite/idb_sqflite.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
import 'package:idb_sqflite/idb_sqflite.dart' as mDatabase; 
abstract class DatabaseHelper {

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
    var factory = getIdbFactorySqflite(databaseFactory);

    log('D/ Init the database.');
    WidgetsFlutterBinding.ensureInitialized();
    // Open database
    _database = await factory.open(
      _databaseName, 
      version: _version,
      onUpgradeNeeded: (VersionChangeEvent event){
        mDatabase.Database databaseEvent = event.database;
        databaseEvent.createObjectStore(table, autoIncrement: true);
      }
    );
    
    /**
    await openDatabase(
      join(await getDatabasesPath(), _databaseName),
      onCreate: (db, version) {
        return db.execute(
          'CREATE TABLE client ('
                          '$columnId INTEGER PRIMARY KEY NOT NULL,'
                          '$columnName STRING, '
                          '$columnQuestions STRING,' 
                          '$columnComplete BOOLEAN)',
        );
      },
      version: _version,
    );
    **/
    
    var kafka = Clients(
      id: 0,
      name: 'Kafka',
      complete: true,
    );
    await insertClient(kafka);
    print(await getClients());
  }
  // Define a function that inserts clients into the database
  Future<void> insertClient(Clients client) async {
    // Get a reference to the database.
    final db = await database;
    // Transaction variable
    final mTransaction = db.transaction(table, idbModeReadWrite);
    // Update client table via transaction
    key = await store.put(client.toMap());
    await mTransaction.completed; 

    /*await db.insert(
      'clients',
      client.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );*/
  }

  // A method that retrieves all the clients from the clients table.
  Future<List<Clients>> getClients() async {
    // Get a reference to the database.
    final db = await database;

    // Getting transactioned client as List
    final clients =  db.transaction(table, idbModeReadOnly) as List<Map<String, dynamic>>;
    // It returns a object and convert it to maps
    final List<Map<String, dynamic>> maps = clients;// Here was .query()

    log('The transaction value is ${db.transaction(table, idbModeReadOnly)}');
    log('The clients value is ${clients.toString()}');

    var value = await store.getObject(key);
    
    // Convert the List<Map<String, dynamic> into a List<Client>.
    return List.generate(maps.length, (i) {
      return Clients(
        id: maps[i]['id'],
        name: maps[i]['name'],
        questions: maps[i]['questions'],
        complete: maps[i]['complete'],
      );
    });
  }

  /**
  Future<void> updateClient(Clients client) async {
    // Get a reference to the database.
    final db = await database;
    // Transaction variable
    final mTransaction = db.transaction(table, idbModeReadWrite);
    // Update client table via transaction
    var store = mTransaction.objectStore(table);
    var key = await store.put(client.toMap());
    await mTransaction.completed; 

    await db.update(
      'clients',
      client.toMap(),
      // Ensure that the Clients has a matching id.
      where: 'id = ?',
      // Pass the Client's id as a whereArg to prevent SQL injection.
      whereArgs: [client.id],
    );
    
  }
  **/

  /**
  Future<void> deleteClient(int id) async {
    final db = await database;

    await db.delete(
      'clients',
      where: 'id = ?',
      whereArgs: [id],
    );
  }
  **/

}