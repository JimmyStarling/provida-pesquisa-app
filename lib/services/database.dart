import 'dart:async';
import 'dart:developer';
import 'package:app_pesquisa_de_satisfacao/models/client.dart';
import 'package:flutter/widgets.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
abstract class DatabaseHelper {

	static int get _version => 1;

  static Database _database;
  static final _databaseName = "banco_pesquisa.db";

  static final table = 'clients';

  static final columnId = '_id';
  static final columnName = 'name';
  static final columnComplete = 'complete';

  Future<Database> get database async {
    if (_database != null)
    return _database;

    // if _database is null we instantiate it
    _database = await initDatabase();
    return _database;
  }

  initDatabase () async {
    log('D/ Init the database.');
    WidgetsFlutterBinding.ensureInitialized();
    // Open database
    _database = await openDatabase(
      join(await getDatabasesPath(), _databaseName),
      onCreate: (db, version) {
        return db.execute(
          'CREATE TABLE client ('
                          '$columnId INTEGER PRIMARY KEY NOT NULL,'
                          '$columnName STRING, ' 
                          '$columnComplete BOOLEAN)',
        );
      },
      version: _version,
    );
    
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

    await db.insert(
      'clients',
      client.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  // A method that retrieves all the clients from the clients table.
  Future<List<Clients>> getClients() async {
    // Get a reference to the database.
    final db = await database;

    // Query the table for all The Dogs.
    final List<Map<String, dynamic>> maps = await db.query('clients');

    // Convert the List<Map<String, dynamic> into a List<Client>.
    return List.generate(maps.length, (i) {
      return Clients(
        id: maps[i]['id'],
        name: maps[i]['name'],
        complete: maps[i]['complete'],
      );
    });
  }

  Future<void> updateClient(Clients client) async {
    // Get a reference to the database.
    final db = await database;

    // Update the given Dog.
    await db.update(
      'clients',
      client.toMap(),
      // Ensure that the Clients has a matching id.
      where: 'id = ?',
      // Pass the Client's id as a whereArg to prevent SQL injection.
      whereArgs: [client.id],
    );
  }

  Future<void> deleteClient(int id) async {
    final db = await database;

    await db.delete(
      'clients',
      where: 'id = ?',
      whereArgs: [id],
    );
  }

}