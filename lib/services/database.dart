import 'dart:async';
import 'package:app_pesquisa_de_satisfacao/models/model.dart';
import 'package:sqflite/sqflite.dart';

abstract class DB {

	static Database _db;

	static int get _version => 1;
  // Init the database with path and version
	static Future<void> init() async {

		if (_db != null) { return; }

		try {
			String _path = await getDatabasesPath() + 'pesquisa_db';
			_db = await openDatabase(_path, version: _version, onCreate: onCreate);
		}
		catch(ex) { 
			print(ex);
		}
	}

	static void onCreate(Database db, int version) async =>
		await db.execute('CREATE TABLE client ('
                      'id INTEGER PRIMARY KEY NOT NULL,'
                      'name STRING, ' 
                      'surname STRING, ' 
                      'complete BOOLEAN)');

  // Query data
	static Future<List<Map<String, dynamic>>> query(String table) async => _db.query(table);
  // Insert data
	static Future<int> insert(String table, Model model) async =>
		await _db.insert(table, model.toMap());
	// Update data
	static Future<int> update(String table, Model model) async =>
		await _db.update(table, model.toMap(), where: 'id = ?', whereArgs: [model.id]);
  // Delete data
	static Future<int> delete(String table, Model model) async =>
		await _db.delete(table, where: 'id = ?', whereArgs: [model.id]);
}