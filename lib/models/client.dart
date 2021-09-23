import 'package:hive/hive.dart';
part 'client.g.dart';

@HiveType(typeId: 0)
class Client extends HiveObject {

  @HiveField(0)
	String name;
  
  @HiveField(1)
  String questions;

  @HiveField(2)
  DateTime createdDate;

  @HiveField(3)
	bool completed = false;

}