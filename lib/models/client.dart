import 'package:app_pesquisa_de_satisfacao/models/model.dart';

class Clients extends Model {

	static String table = 'clients';

	int id;
	String name;
  String questions;
	bool complete;

	Clients({ this.id, this.name, this.questions, this.complete });

	Map<String, dynamic> toMap() {

		Map<String, dynamic> map = {
			'name': name,
      'questions': questions,
			'complete': complete
		};

		if (id != null) { map['id'] = id; }
		return map;
	}

	static Clients fromMap(Map<String, dynamic> map) {
		
		return Clients(
			id: map['id'],
			name: map['name'],
      questions: map['questions'],
			complete: map['complete'] == 1
		);
	}
}