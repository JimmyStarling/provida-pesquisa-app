import 'package:app_pesquisa_de_satisfacao/models/client.dart';
import 'package:hive/hive.dart';

class Boxes {
  static Box<Client> getClients() =>
      Hive.box<Client>('clients');
}