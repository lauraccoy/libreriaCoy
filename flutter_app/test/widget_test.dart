import 'package:flutter_test/flutter_test.dart';
import 'package:libreriacoy_flutter/main.dart';

void main() {
  testWidgets('App starts', (WidgetTester tester) async {
    await tester.pumpWidget(const LibreriaCoyApp());
    await tester.pumpAndSettle(); // deja que cargue la primera pantalla
    expect(find.text('LibreriaCoy'), findsOneWidget); // si sale en AppBar/title
  });
}

