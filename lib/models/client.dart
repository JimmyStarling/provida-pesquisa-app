import 'package:app_pesquisa_de_satisfacao/models/model.dart';
import 'package:hive/hive.dart';
part 'client.g.dart';

@HiveType(typeId: 0)
class Client extends HiveObject {

  @HiveField(0)
	String name;
  @HiveField(1)
  String questions;
  @HiveField(2)
  DateTime created;
  @HiveField(3)
	bool complete = false;

	Client(this.name, this.questions, this.created, this.complete);

  Map<String, dynamic> toMap() {

		Map<String, dynamic> map = {
			'name': name,
      'questions': questions,
			'complete': complete
		};

		return map;
	}

	static Client fromMap(Map<String, dynamic> map) {
		return Client(
			map['id'],
			map['name'],
      map['questions'],
			map['complete'] == 1
		);
	}

}