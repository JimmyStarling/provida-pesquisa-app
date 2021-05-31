import 'package:app_pesquisa_de_satisfacao/models/model.dart';

class Client extends Model {

	static String table = 'client';

	int id;
	String name;
  String surname;
	bool complete;

	Client({ this.id, this.name, this.surname, this.complete });

	Map<String, dynamic> toMap() {

		Map<String, dynamic> map = {
			'name': name,
      'surname': surname,
			'complete': complete
		};

		if (id != null) { map['id'] = id; }
		return map;
	}

	static Client fromMap(Map<String, dynamic> map) {
		
		return Client(
			id: map['id'],
			name: map['name'],
      surname: map['surname'],
			complete: map['complete'] == 1
		);
	}
}