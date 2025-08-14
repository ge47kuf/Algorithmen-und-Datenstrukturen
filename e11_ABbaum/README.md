# AB-Baum — Aufgabe (a,b)-Bäume

## Kurzbeschreibung

Implementiere in **Java** einen **(a,b)-Baum** (auch (a,b)-search-tree genannt). Es soll die reine Baumstruktur (Innere Knoten und Blätter) implementiert werden — die Blattklasse `ABTreeLeaf` ist vorgegeben und enthält selbst keine Payload-Daten. Eltern-Referenzen sind **nicht** erlaubt. Implementiere die Kernoperationen Einfügen (`insert`), Löschen (`remove`), Suche (`find`), Höhenberechnung (`height`) sowie die Validitätsprüfung (`validAB`). Optional: eine `dot()`-Methode zur Ausgabe im Graphviz-Format für Debugging.

---

## Sprache & Format

* Sprache: **Java** (nutze die im Template vorgegebenen Klassen-/Methodensignaturen).
* Ausgabedatei: `README.md` (dieses Dokument).
* Keine Parent-Pointer in Knoten.
* Blätter sind Instanzen von `ABTreeLeaf` (halten keine Nutzdaten in dieser Aufgabe).

---

## Aufgabe — was zu implementieren ist

### Kernmethoden (Pflicht)

Implementiere in den vorgegebenen Klassen mindestens folgende Methoden (Signaturen an dein Template anpassen):

* `boolean insert(int key)`
  Fügt `key` in den Baum ein, **falls noch nicht vorhanden**. Falls `key` bereits existiert, soll die Methode nichts tun (gibt z. B. `false` zurück). Beim Aufspalten gilt: das Element am Index `⌊b/2⌋` wandert nach oben.

* `boolean remove(int key)`
  Löscht `key` aus dem Baum. Rückgabe `true`, wenn gefunden und gelöscht, sonst `false`. Beim Löschen aus inneren Knoten verwende stets den **symmetrischen Vorgänger** (nächst kleinerer Wert) zur Ersetzung. Beim „Stehlen“ zuerst linken Nachbarn versuchen, dann rechten. Beim Verschmelzen standardmäßig mit rechtem Nachbarn verschmelzen; falls kein rechter Nachbar, mit linkem Nachbarn verschmelzen.

* `boolean find(int key)`
  Sucht den Schlüssel und gibt `true` zurück, falls vorhanden.

* `int height()`
  Liefert die Höhe des Baumes: leerer Baum → `0`, Baum mit einem Wert → `1`.

* `boolean validAB(int a, int b)`
  Prüft, ob der Baum die formalen Anforderungen an einen (a,b)-Baum erfüllt (siehe Abschnitt *Validitätsbedingungen*).

### Optional / Hilfreich

* `String dot()` — Graphviz-Export (zum Debuggen; nicht Teil der Korrektheit, aber hilfreich).
* Hilfsmethoden wie `min()`/`max()` auf Knoten (nur falls du sie brauchst).

---

## Anforderungen / Einschränkungen (wichtig)

* Keine Parent-Pointer in den Knoten.
* Blätter sind Instanzen von `ABTreeLeaf` und enthalten in dieser Aufgabe keine Datensätze.
* Beim Splitten: das Element an Index `⌊b/2⌋` steigt in den Elternknoten auf.
* Beim Einfügen: kein Duplikat einfügen (wenn Schlüssel schon existiert, nichts tun).
* Beim Löschen: verwende immer den symmetrischen Vorgänger (kein Suchen/Nutzen des Nachfolgers).
* Beim Ausgleichen nach Entfernen:

  * Versuch zuerst **Stehlen** vom linken Nachbarn; falls nicht möglich, vom rechten Nachbarn.
  * Beim Merge: verschmelze mit dem **rechten** Nachbarn; falls kein rechter Nachbar vorhanden ist, mit dem linken.

---

## Validitätsbedingungen (`validAB`)

`validAB(a, b)` soll prüfen, ob der Baum ein gültiger (a,b)-Baum ist. Konkret müssen folgende Eigenschaften erfüllt sein:

1. **Gleiche Höhe aller Teilbäume**
   Für jeden inneren Knoten haben alle Kind-Teilbäume die gleiche Höhe.

2. **Anzahl Kinder**
   Jeder innere Knoten (außer eventuell die Wurzel) hat mindestens `a` und höchstens `b` Kinder. Die Wurzel darf weniger Kinder haben (siehe Vorlesung / Template-Hinweise).

3. **Sortierung in Knoten / Suchbaum-Eigenschaft**

   * Die Schlüssellisten in jedem inneren Knoten sind strikt aufsteigend.
   * Der i-te Kind-Teilbaum enthält nur Schlüssel `e` mit `key_i < e < key_{i+1}` (bzw. links `<=` / rechts `>=` je nach Konvention; halte dich an die Konvention im Template).
   * Alle Schlüssel des ganz linken Teilbaums sind kleiner als alle Schlüssel des aktuellen Knotens; alle Schlüssel des ganz rechten Teilbaums sind größer als alle Schlüssel des aktuellen Knotens.

4. **Konsistenz / keine Zyklen**
   Der Baum darf **keine Kreise** enthalten (behandle Vater→Kind-Kanten als ungerichtet; Traversal darf keinen Knoten mehrfach erreichen).

`validAB` wird z. B. zur Debugging-Unterstützung und in Tests verwendet — sie muss zuverlässig falsche Strukturen entdecken.

---

## Verhalten bei Aufspalten / Ausgleichen (Kurzbeschreibung, keine Implementationsanleitung)

* Wenn ein Knoten mehr als `b` Kinder/Schlüssel erhält, muss er aufgespalten werden; das mittlere Element (Index `⌊b/2⌋`) wird in den Eltern gehoben.
* Beim Entfernen kann ein Knoten unter `a` Kinder fallen; dann muss ausgeglichen werden, entweder durch **Stehlen** (von linker oder rechter Nachbar) oder durch **Verschmelzen** mit einem Nachbarn.
* Beim Stehlen zuerst linker Nachbar, falls dieser genügend Kinder liefert; sonst rechter.
* Beim Verschmelzen mit rechtem Nachbarn zusammenführen; falls kein rechter vorhanden ist, mit linkem verschmelzen.

> Diese Abschnitte beschreiben erwartetes Verhalten — **implementiere sie** entsprechend dem Template und der Vorlesungsdefinition; verfahre aber so, dass Tests die geforderte Struktur erreichen.

---

## Beispiele (Veranschaulichung)

Das Template enthält ein illustratives Beispiel für einen **(2,4)-Baum** mit folgender Einfüge-Reihenfolge:

```
Die Einfügereihenfolge in den (2,4)-Baum war dabei: 109, 23, 49, 180, 120, 163, 172, 130, 95, 156, 99, 39, 178, 197, 71, 194, 118, 88
Die Löschreihenfolge darauf war: 95, 194, 23, 118, 109, 178, 71, 88, 197, 156, 99, 163, 49, 172, 120, 130, 180, 39

digraph {
node [shape=record];
}


Inserting: 109
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 109|<f2> "];
struct0:<f0> -> struct1;
struct1 [label=leaf, shape=ellipse];
struct0:<f2> -> struct2;
struct2 [label=leaf, shape=ellipse];
}


Inserting: 23
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 23|<f2> |<f3> 109|<f4> "];
struct0:<f0> -> struct1;
struct1 [label=leaf, shape=ellipse];
struct0:<f2> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct0:<f4> -> struct3;
struct3 [label=leaf, shape=ellipse];
}


Inserting: 49
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 23|<f2> |<f3> 49|<f4> |<f5> 109|<f6> "];
struct0:<f0> -> struct1;
struct1 [label=leaf, shape=ellipse];
struct0:<f2> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct0:<f4> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct0:<f6> -> struct4;
struct4 [label=leaf, shape=ellipse];
}


Inserting: 180
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 109|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 23|<f2> |<f3> 49|<f4> "];
struct1:<f0> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct1:<f2> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct1:<f4> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct0:<f2> -> struct5;
struct5 [label="<f0> |<f1> 180|<f2> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
}


Inserting: 120
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 109|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 23|<f2> |<f3> 49|<f4> "];
struct1:<f0> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct1:<f2> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct1:<f4> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct0:<f2> -> struct5;
struct5 [label="<f0> |<f1> 120|<f2> |<f3> 180|<f4> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct5:<f4> -> struct8;
struct8 [label=leaf, shape=ellipse];
}


Inserting: 163
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 109|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 23|<f2> |<f3> 49|<f4> "];
struct1:<f0> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct1:<f2> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct1:<f4> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct0:<f2> -> struct5;
struct5 [label="<f0> |<f1> 120|<f2> |<f3> 163|<f4> |<f5> 180|<f6> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct5:<f4> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct5:<f6> -> struct9;
struct9 [label=leaf, shape=ellipse];
}


Inserting: 172
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 109|<f2> |<f3> 172|<f4> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 23|<f2> |<f3> 49|<f4> "];
struct1:<f0> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct1:<f2> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct1:<f4> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct0:<f2> -> struct5;
struct5 [label="<f0> |<f1> 120|<f2> |<f3> 163|<f4> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct5:<f4> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct0:<f4> -> struct9;
struct9 [label="<f0> |<f1> 180|<f2> "];
struct9:<f0> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct9:<f2> -> struct11;
struct11 [label=leaf, shape=ellipse];
}


Inserting: 130
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 109|<f2> |<f3> 172|<f4> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 23|<f2> |<f3> 49|<f4> "];
struct1:<f0> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct1:<f2> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct1:<f4> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct0:<f2> -> struct5;
struct5 [label="<f0> |<f1> 120|<f2> |<f3> 130|<f4> |<f5> 163|<f6> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct5:<f4> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct5:<f6> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct0:<f4> -> struct10;
struct10 [label="<f0> |<f1> 180|<f2> "];
struct10:<f0> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct10:<f2> -> struct12;
struct12 [label=leaf, shape=ellipse];
}


Inserting: 95
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 109|<f2> |<f3> 172|<f4> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 23|<f2> |<f3> 49|<f4> |<f5> 95|<f6> "];
struct1:<f0> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct1:<f2> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct1:<f4> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct1:<f6> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct0:<f2> -> struct6;
struct6 [label="<f0> |<f1> 120|<f2> |<f3> 130|<f4> |<f5> 163|<f6> "];
struct6:<f0> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct6:<f2> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct6:<f4> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct6:<f6> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct0:<f4> -> struct11;
struct11 [label="<f0> |<f1> 180|<f2> "];
struct11:<f0> -> struct12;
struct12 [label=leaf, shape=ellipse];
struct11:<f2> -> struct13;
struct13 [label=leaf, shape=ellipse];
}


Inserting: 156
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 109|<f2> |<f3> 156|<f4> |<f5> 172|<f6> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 23|<f2> |<f3> 49|<f4> |<f5> 95|<f6> "];
struct1:<f0> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct1:<f2> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct1:<f4> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct1:<f6> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct0:<f2> -> struct6;
struct6 [label="<f0> |<f1> 120|<f2> |<f3> 130|<f4> "];
struct6:<f0> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct6:<f2> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct6:<f4> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct0:<f4> -> struct10;
struct10 [label="<f0> |<f1> 163|<f2> "];
struct10:<f0> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct10:<f2> -> struct12;
struct12 [label=leaf, shape=ellipse];
struct0:<f6> -> struct13;
struct13 [label="<f0> |<f1> 180|<f2> "];
struct13:<f0> -> struct14;
struct14 [label=leaf, shape=ellipse];
struct13:<f2> -> struct15;
struct15 [label=leaf, shape=ellipse];
}


Inserting: 99
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 95|<f2> |<f3> 109|<f4> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 23|<f2> |<f3> 49|<f4> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct2:<f4> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct1:<f2> -> struct6;
struct6 [label="<f0> |<f1> 99|<f2> "];
struct6:<f0> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct6:<f2> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct1:<f4> -> struct9;
struct9 [label="<f0> |<f1> 120|<f2> |<f3> 130|<f4> "];
struct9:<f0> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct9:<f2> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct9:<f4> -> struct12;
struct12 [label=leaf, shape=ellipse];
struct0:<f2> -> struct13;
struct13 [label="<f0> |<f1> 172|<f2> "];
struct13:<f0> -> struct14;
struct14 [label="<f0> |<f1> 163|<f2> "];
struct14:<f0> -> struct15;
struct15 [label=leaf, shape=ellipse];
struct14:<f2> -> struct16;
struct16 [label=leaf, shape=ellipse];
struct13:<f2> -> struct17;
struct17 [label="<f0> |<f1> 180|<f2> "];
struct17:<f0> -> struct18;
struct18 [label=leaf, shape=ellipse];
struct17:<f2> -> struct19;
struct19 [label=leaf, shape=ellipse];
}


Inserting: 39
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 95|<f2> |<f3> 109|<f4> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 23|<f2> |<f3> 39|<f4> |<f5> 49|<f6> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct2:<f4> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct2:<f6> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct1:<f2> -> struct7;
struct7 [label="<f0> |<f1> 99|<f2> "];
struct7:<f0> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct7:<f2> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct1:<f4> -> struct10;
struct10 [label="<f0> |<f1> 120|<f2> |<f3> 130|<f4> "];
struct10:<f0> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct10:<f2> -> struct12;
struct12 [label=leaf, shape=ellipse];
struct10:<f4> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct0:<f2> -> struct14;
struct14 [label="<f0> |<f1> 172|<f2> "];
struct14:<f0> -> struct15;
struct15 [label="<f0> |<f1> 163|<f2> "];
struct15:<f0> -> struct16;
struct16 [label=leaf, shape=ellipse];
struct15:<f2> -> struct17;
struct17 [label=leaf, shape=ellipse];
struct14:<f2> -> struct18;
struct18 [label="<f0> |<f1> 180|<f2> "];
struct18:<f0> -> struct19;
struct19 [label=leaf, shape=ellipse];
struct18:<f2> -> struct20;
struct20 [label=leaf, shape=ellipse];
}


Inserting: 178
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 95|<f2> |<f3> 109|<f4> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 23|<f2> |<f3> 39|<f4> |<f5> 49|<f6> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct2:<f4> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct2:<f6> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct1:<f2> -> struct7;
struct7 [label="<f0> |<f1> 99|<f2> "];
struct7:<f0> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct7:<f2> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct1:<f4> -> struct10;
struct10 [label="<f0> |<f1> 120|<f2> |<f3> 130|<f4> "];
struct10:<f0> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct10:<f2> -> struct12;
struct12 [label=leaf, shape=ellipse];
struct10:<f4> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct0:<f2> -> struct14;
struct14 [label="<f0> |<f1> 172|<f2> "];
struct14:<f0> -> struct15;
struct15 [label="<f0> |<f1> 163|<f2> "];
struct15:<f0> -> struct16;
struct16 [label=leaf, shape=ellipse];
struct15:<f2> -> struct17;
struct17 [label=leaf, shape=ellipse];
struct14:<f2> -> struct18;
struct18 [label="<f0> |<f1> 178|<f2> |<f3> 180|<f4> "];
struct18:<f0> -> struct19;
struct19 [label=leaf, shape=ellipse];
struct18:<f2> -> struct20;
struct20 [label=leaf, shape=ellipse];
struct18:<f4> -> struct21;
struct21 [label=leaf, shape=ellipse];
}


Inserting: 197
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 95|<f2> |<f3> 109|<f4> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 23|<f2> |<f3> 39|<f4> |<f5> 49|<f6> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct2:<f4> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct2:<f6> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct1:<f2> -> struct7;
struct7 [label="<f0> |<f1> 99|<f2> "];
struct7:<f0> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct7:<f2> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct1:<f4> -> struct10;
struct10 [label="<f0> |<f1> 120|<f2> |<f3> 130|<f4> "];
struct10:<f0> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct10:<f2> -> struct12;
struct12 [label=leaf, shape=ellipse];
struct10:<f4> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct0:<f2> -> struct14;
struct14 [label="<f0> |<f1> 172|<f2> "];
struct14:<f0> -> struct15;
struct15 [label="<f0> |<f1> 163|<f2> "];
struct15:<f0> -> struct16;
struct16 [label=leaf, shape=ellipse];
struct15:<f2> -> struct17;
struct17 [label=leaf, shape=ellipse];
struct14:<f2> -> struct18;
struct18 [label="<f0> |<f1> 178|<f2> |<f3> 180|<f4> |<f5> 197|<f6> "];
struct18:<f0> -> struct19;
struct19 [label=leaf, shape=ellipse];
struct18:<f2> -> struct20;
struct20 [label=leaf, shape=ellipse];
struct18:<f4> -> struct21;
struct21 [label=leaf, shape=ellipse];
struct18:<f6> -> struct22;
struct22 [label=leaf, shape=ellipse];
}


Inserting: 71
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> |<f3> 95|<f4> |<f5> 109|<f6> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 23|<f2> |<f3> 39|<f4> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct2:<f4> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct1:<f2> -> struct6;
struct6 [label="<f0> |<f1> 71|<f2> "];
struct6:<f0> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct6:<f2> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct1:<f4> -> struct9;
struct9 [label="<f0> |<f1> 99|<f2> "];
struct9:<f0> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct9:<f2> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct1:<f6> -> struct12;
struct12 [label="<f0> |<f1> 120|<f2> |<f3> 130|<f4> "];
struct12:<f0> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct12:<f2> -> struct14;
struct14 [label=leaf, shape=ellipse];
struct12:<f4> -> struct15;
struct15 [label=leaf, shape=ellipse];
struct0:<f2> -> struct16;
struct16 [label="<f0> |<f1> 172|<f2> "];
struct16:<f0> -> struct17;
struct17 [label="<f0> |<f1> 163|<f2> "];
struct17:<f0> -> struct18;
struct18 [label=leaf, shape=ellipse];
struct17:<f2> -> struct19;
struct19 [label=leaf, shape=ellipse];
struct16:<f2> -> struct20;
struct20 [label="<f0> |<f1> 178|<f2> |<f3> 180|<f4> |<f5> 197|<f6> "];
struct20:<f0> -> struct21;
struct21 [label=leaf, shape=ellipse];
struct20:<f2> -> struct22;
struct22 [label=leaf, shape=ellipse];
struct20:<f4> -> struct23;
struct23 [label=leaf, shape=ellipse];
struct20:<f6> -> struct24;
struct24 [label=leaf, shape=ellipse];
}


Inserting: 194
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> |<f3> 95|<f4> |<f5> 109|<f6> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 23|<f2> |<f3> 39|<f4> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct2:<f4> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct1:<f2> -> struct6;
struct6 [label="<f0> |<f1> 71|<f2> "];
struct6:<f0> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct6:<f2> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct1:<f4> -> struct9;
struct9 [label="<f0> |<f1> 99|<f2> "];
struct9:<f0> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct9:<f2> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct1:<f6> -> struct12;
struct12 [label="<f0> |<f1> 120|<f2> |<f3> 130|<f4> "];
struct12:<f0> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct12:<f2> -> struct14;
struct14 [label=leaf, shape=ellipse];
struct12:<f4> -> struct15;
struct15 [label=leaf, shape=ellipse];
struct0:<f2> -> struct16;
struct16 [label="<f0> |<f1> 172|<f2> |<f3> 194|<f4> "];
struct16:<f0> -> struct17;
struct17 [label="<f0> |<f1> 163|<f2> "];
struct17:<f0> -> struct18;
struct18 [label=leaf, shape=ellipse];
struct17:<f2> -> struct19;
struct19 [label=leaf, shape=ellipse];
struct16:<f2> -> struct20;
struct20 [label="<f0> |<f1> 178|<f2> |<f3> 180|<f4> "];
struct20:<f0> -> struct21;
struct21 [label=leaf, shape=ellipse];
struct20:<f2> -> struct22;
struct22 [label=leaf, shape=ellipse];
struct20:<f4> -> struct23;
struct23 [label=leaf, shape=ellipse];
struct16:<f4> -> struct24;
struct24 [label="<f0> |<f1> 197|<f2> "];
struct24:<f0> -> struct25;
struct25 [label=leaf, shape=ellipse];
struct24:<f2> -> struct26;
struct26 [label=leaf, shape=ellipse];
}


Inserting: 118
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> |<f3> 95|<f4> |<f5> 109|<f6> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 23|<f2> |<f3> 39|<f4> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct2:<f4> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct1:<f2> -> struct6;
struct6 [label="<f0> |<f1> 71|<f2> "];
struct6:<f0> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct6:<f2> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct1:<f4> -> struct9;
struct9 [label="<f0> |<f1> 99|<f2> "];
struct9:<f0> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct9:<f2> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct1:<f6> -> struct12;
struct12 [label="<f0> |<f1> 118|<f2> |<f3> 120|<f4> |<f5> 130|<f6> "];
struct12:<f0> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct12:<f2> -> struct14;
struct14 [label=leaf, shape=ellipse];
struct12:<f4> -> struct15;
struct15 [label=leaf, shape=ellipse];
struct12:<f6> -> struct16;
struct16 [label=leaf, shape=ellipse];
struct0:<f2> -> struct17;
struct17 [label="<f0> |<f1> 172|<f2> |<f3> 194|<f4> "];
struct17:<f0> -> struct18;
struct18 [label="<f0> |<f1> 163|<f2> "];
struct18:<f0> -> struct19;
struct19 [label=leaf, shape=ellipse];
struct18:<f2> -> struct20;
struct20 [label=leaf, shape=ellipse];
struct17:<f2> -> struct21;
struct21 [label="<f0> |<f1> 178|<f2> |<f3> 180|<f4> "];
struct21:<f0> -> struct22;
struct22 [label=leaf, shape=ellipse];
struct21:<f2> -> struct23;
struct23 [label=leaf, shape=ellipse];
struct21:<f4> -> struct24;
struct24 [label=leaf, shape=ellipse];
struct17:<f4> -> struct25;
struct25 [label="<f0> |<f1> 197|<f2> "];
struct25:<f0> -> struct26;
struct26 [label=leaf, shape=ellipse];
struct25:<f2> -> struct27;
struct27 [label=leaf, shape=ellipse];
}


Inserting: 88
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> |<f3> 95|<f4> |<f5> 109|<f6> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 23|<f2> |<f3> 39|<f4> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct2:<f4> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct1:<f2> -> struct6;
struct6 [label="<f0> |<f1> 71|<f2> |<f3> 88|<f4> "];
struct6:<f0> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct6:<f2> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct6:<f4> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct1:<f4> -> struct10;
struct10 [label="<f0> |<f1> 99|<f2> "];
struct10:<f0> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct10:<f2> -> struct12;
struct12 [label=leaf, shape=ellipse];
struct1:<f6> -> struct13;
struct13 [label="<f0> |<f1> 118|<f2> |<f3> 120|<f4> |<f5> 130|<f6> "];
struct13:<f0> -> struct14;
struct14 [label=leaf, shape=ellipse];
struct13:<f2> -> struct15;
struct15 [label=leaf, shape=ellipse];
struct13:<f4> -> struct16;
struct16 [label=leaf, shape=ellipse];
struct13:<f6> -> struct17;
struct17 [label=leaf, shape=ellipse];
struct0:<f2> -> struct18;
struct18 [label="<f0> |<f1> 172|<f2> |<f3> 194|<f4> "];
struct18:<f0> -> struct19;
struct19 [label="<f0> |<f1> 163|<f2> "];
struct19:<f0> -> struct20;
struct20 [label=leaf, shape=ellipse];
struct19:<f2> -> struct21;
struct21 [label=leaf, shape=ellipse];
struct18:<f2> -> struct22;
struct22 [label="<f0> |<f1> 178|<f2> |<f3> 180|<f4> "];
struct22:<f0> -> struct23;
struct23 [label=leaf, shape=ellipse];
struct22:<f2> -> struct24;
struct24 [label=leaf, shape=ellipse];
struct22:<f4> -> struct25;
struct25 [label=leaf, shape=ellipse];
struct18:<f4> -> struct26;
struct26 [label="<f0> |<f1> 197|<f2> "];
struct26:<f0> -> struct27;
struct27 [label=leaf, shape=ellipse];
struct26:<f2> -> struct28;
struct28 [label=leaf, shape=ellipse];
}


Ab hier wird gelöscht!!!


Deleting: 95
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> |<f3> 88|<f4> |<f5> 109|<f6> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 23|<f2> |<f3> 39|<f4> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct2:<f4> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct1:<f2> -> struct6;
struct6 [label="<f0> |<f1> 71|<f2> "];
struct6:<f0> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct6:<f2> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct1:<f4> -> struct9;
struct9 [label="<f0> |<f1> 99|<f2> "];
struct9:<f0> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct9:<f2> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct1:<f6> -> struct12;
struct12 [label="<f0> |<f1> 118|<f2> |<f3> 120|<f4> |<f5> 130|<f6> "];
struct12:<f0> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct12:<f2> -> struct14;
struct14 [label=leaf, shape=ellipse];
struct12:<f4> -> struct15;
struct15 [label=leaf, shape=ellipse];
struct12:<f6> -> struct16;
struct16 [label=leaf, shape=ellipse];
struct0:<f2> -> struct17;
struct17 [label="<f0> |<f1> 172|<f2> |<f3> 194|<f4> "];
struct17:<f0> -> struct18;
struct18 [label="<f0> |<f1> 163|<f2> "];
struct18:<f0> -> struct19;
struct19 [label=leaf, shape=ellipse];
struct18:<f2> -> struct20;
struct20 [label=leaf, shape=ellipse];
struct17:<f2> -> struct21;
struct21 [label="<f0> |<f1> 178|<f2> |<f3> 180|<f4> "];
struct21:<f0> -> struct22;
struct22 [label=leaf, shape=ellipse];
struct21:<f2> -> struct23;
struct23 [label=leaf, shape=ellipse];
struct21:<f4> -> struct24;
struct24 [label=leaf, shape=ellipse];
struct17:<f4> -> struct25;
struct25 [label="<f0> |<f1> 197|<f2> "];
struct25:<f0> -> struct26;
struct26 [label=leaf, shape=ellipse];
struct25:<f2> -> struct27;
struct27 [label=leaf, shape=ellipse];
}


Deleting: 194
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> |<f3> 88|<f4> |<f5> 109|<f6> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 23|<f2> |<f3> 39|<f4> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct2:<f4> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct1:<f2> -> struct6;
struct6 [label="<f0> |<f1> 71|<f2> "];
struct6:<f0> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct6:<f2> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct1:<f4> -> struct9;
struct9 [label="<f0> |<f1> 99|<f2> "];
struct9:<f0> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct9:<f2> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct1:<f6> -> struct12;
struct12 [label="<f0> |<f1> 118|<f2> |<f3> 120|<f4> |<f5> 130|<f6> "];
struct12:<f0> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct12:<f2> -> struct14;
struct14 [label=leaf, shape=ellipse];
struct12:<f4> -> struct15;
struct15 [label=leaf, shape=ellipse];
struct12:<f6> -> struct16;
struct16 [label=leaf, shape=ellipse];
struct0:<f2> -> struct17;
struct17 [label="<f0> |<f1> 172|<f2> |<f3> 180|<f4> "];
struct17:<f0> -> struct18;
struct18 [label="<f0> |<f1> 163|<f2> "];
struct18:<f0> -> struct19;
struct19 [label=leaf, shape=ellipse];
struct18:<f2> -> struct20;
struct20 [label=leaf, shape=ellipse];
struct17:<f2> -> struct21;
struct21 [label="<f0> |<f1> 178|<f2> "];
struct21:<f0> -> struct22;
struct22 [label=leaf, shape=ellipse];
struct21:<f2> -> struct23;
struct23 [label=leaf, shape=ellipse];
struct17:<f4> -> struct24;
struct24 [label="<f0> |<f1> 197|<f2> "];
struct24:<f0> -> struct25;
struct25 [label=leaf, shape=ellipse];
struct24:<f2> -> struct26;
struct26 [label=leaf, shape=ellipse];
}


Deleting: 23
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> |<f3> 88|<f4> |<f5> 109|<f6> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 39|<f2> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct1:<f2> -> struct5;
struct5 [label="<f0> |<f1> 71|<f2> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct1:<f4> -> struct8;
struct8 [label="<f0> |<f1> 99|<f2> "];
struct8:<f0> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct8:<f2> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct1:<f6> -> struct11;
struct11 [label="<f0> |<f1> 118|<f2> |<f3> 120|<f4> |<f5> 130|<f6> "];
struct11:<f0> -> struct12;
struct12 [label=leaf, shape=ellipse];
struct11:<f2> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct11:<f4> -> struct14;
struct14 [label=leaf, shape=ellipse];
struct11:<f6> -> struct15;
struct15 [label=leaf, shape=ellipse];
struct0:<f2> -> struct16;
struct16 [label="<f0> |<f1> 172|<f2> |<f3> 180|<f4> "];
struct16:<f0> -> struct17;
struct17 [label="<f0> |<f1> 163|<f2> "];
struct17:<f0> -> struct18;
struct18 [label=leaf, shape=ellipse];
struct17:<f2> -> struct19;
struct19 [label=leaf, shape=ellipse];
struct16:<f2> -> struct20;
struct20 [label="<f0> |<f1> 178|<f2> "];
struct20:<f0> -> struct21;
struct21 [label=leaf, shape=ellipse];
struct20:<f2> -> struct22;
struct22 [label=leaf, shape=ellipse];
struct16:<f4> -> struct23;
struct23 [label="<f0> |<f1> 197|<f2> "];
struct23:<f0> -> struct24;
struct24 [label=leaf, shape=ellipse];
struct23:<f2> -> struct25;
struct25 [label=leaf, shape=ellipse];
}


Deleting: 118
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> |<f3> 88|<f4> |<f5> 109|<f6> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 39|<f2> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct1:<f2> -> struct5;
struct5 [label="<f0> |<f1> 71|<f2> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct1:<f4> -> struct8;
struct8 [label="<f0> |<f1> 99|<f2> "];
struct8:<f0> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct8:<f2> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct1:<f6> -> struct11;
struct11 [label="<f0> |<f1> 120|<f2> |<f3> 130|<f4> "];
struct11:<f0> -> struct12;
struct12 [label=leaf, shape=ellipse];
struct11:<f2> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct11:<f4> -> struct14;
struct14 [label=leaf, shape=ellipse];
struct0:<f2> -> struct15;
struct15 [label="<f0> |<f1> 172|<f2> |<f3> 180|<f4> "];
struct15:<f0> -> struct16;
struct16 [label="<f0> |<f1> 163|<f2> "];
struct16:<f0> -> struct17;
struct17 [label=leaf, shape=ellipse];
struct16:<f2> -> struct18;
struct18 [label=leaf, shape=ellipse];
struct15:<f2> -> struct19;
struct19 [label="<f0> |<f1> 178|<f2> "];
struct19:<f0> -> struct20;
struct20 [label=leaf, shape=ellipse];
struct19:<f2> -> struct21;
struct21 [label=leaf, shape=ellipse];
struct15:<f4> -> struct22;
struct22 [label="<f0> |<f1> 197|<f2> "];
struct22:<f0> -> struct23;
struct23 [label=leaf, shape=ellipse];
struct22:<f2> -> struct24;
struct24 [label=leaf, shape=ellipse];
}


Deleting: 109
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> |<f3> 88|<f4> |<f5> 120|<f6> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 39|<f2> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct1:<f2> -> struct5;
struct5 [label="<f0> |<f1> 71|<f2> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct1:<f4> -> struct8;
struct8 [label="<f0> |<f1> 99|<f2> "];
struct8:<f0> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct8:<f2> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct1:<f6> -> struct11;
struct11 [label="<f0> |<f1> 130|<f2> "];
struct11:<f0> -> struct12;
struct12 [label=leaf, shape=ellipse];
struct11:<f2> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct0:<f2> -> struct14;
struct14 [label="<f0> |<f1> 172|<f2> |<f3> 180|<f4> "];
struct14:<f0> -> struct15;
struct15 [label="<f0> |<f1> 163|<f2> "];
struct15:<f0> -> struct16;
struct16 [label=leaf, shape=ellipse];
struct15:<f2> -> struct17;
struct17 [label=leaf, shape=ellipse];
struct14:<f2> -> struct18;
struct18 [label="<f0> |<f1> 178|<f2> "];
struct18:<f0> -> struct19;
struct19 [label=leaf, shape=ellipse];
struct18:<f2> -> struct20;
struct20 [label=leaf, shape=ellipse];
struct14:<f4> -> struct21;
struct21 [label="<f0> |<f1> 197|<f2> "];
struct21:<f0> -> struct22;
struct22 [label=leaf, shape=ellipse];
struct21:<f2> -> struct23;
struct23 [label=leaf, shape=ellipse];
}


Deleting: 178
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> |<f3> 88|<f4> |<f5> 120|<f6> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 39|<f2> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct1:<f2> -> struct5;
struct5 [label="<f0> |<f1> 71|<f2> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct1:<f4> -> struct8;
struct8 [label="<f0> |<f1> 99|<f2> "];
struct8:<f0> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct8:<f2> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct1:<f6> -> struct11;
struct11 [label="<f0> |<f1> 130|<f2> "];
struct11:<f0> -> struct12;
struct12 [label=leaf, shape=ellipse];
struct11:<f2> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct0:<f2> -> struct14;
struct14 [label="<f0> |<f1> 172|<f2> "];
struct14:<f0> -> struct15;
struct15 [label="<f0> |<f1> 163|<f2> "];
struct15:<f0> -> struct16;
struct16 [label=leaf, shape=ellipse];
struct15:<f2> -> struct17;
struct17 [label=leaf, shape=ellipse];
struct14:<f2> -> struct18;
struct18 [label="<f0> |<f1> 180|<f2> |<f3> 197|<f4> "];
struct18:<f0> -> struct19;
struct19 [label=leaf, shape=ellipse];
struct18:<f2> -> struct20;
struct20 [label=leaf, shape=ellipse];
struct18:<f4> -> struct21;
struct21 [label=leaf, shape=ellipse];
}


Deleting: 71
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> |<f3> 120|<f4> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 39|<f2> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct1:<f2> -> struct5;
struct5 [label="<f0> |<f1> 88|<f2> |<f3> 99|<f4> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct5:<f4> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct1:<f4> -> struct9;
struct9 [label="<f0> |<f1> 130|<f2> "];
struct9:<f0> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct9:<f2> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct0:<f2> -> struct12;
struct12 [label="<f0> |<f1> 172|<f2> "];
struct12:<f0> -> struct13;
struct13 [label="<f0> |<f1> 163|<f2> "];
struct13:<f0> -> struct14;
struct14 [label=leaf, shape=ellipse];
struct13:<f2> -> struct15;
struct15 [label=leaf, shape=ellipse];
struct12:<f2> -> struct16;
struct16 [label="<f0> |<f1> 180|<f2> |<f3> 197|<f4> "];
struct16:<f0> -> struct17;
struct17 [label=leaf, shape=ellipse];
struct16:<f2> -> struct18;
struct18 [label=leaf, shape=ellipse];
struct16:<f4> -> struct19;
struct19 [label=leaf, shape=ellipse];
}


Deleting: 88
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> |<f3> 120|<f4> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 39|<f2> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct1:<f2> -> struct5;
struct5 [label="<f0> |<f1> 99|<f2> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct1:<f4> -> struct8;
struct8 [label="<f0> |<f1> 130|<f2> "];
struct8:<f0> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct8:<f2> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct0:<f2> -> struct11;
struct11 [label="<f0> |<f1> 172|<f2> "];
struct11:<f0> -> struct12;
struct12 [label="<f0> |<f1> 163|<f2> "];
struct12:<f0> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct12:<f2> -> struct14;
struct14 [label=leaf, shape=ellipse];
struct11:<f2> -> struct15;
struct15 [label="<f0> |<f1> 180|<f2> |<f3> 197|<f4> "];
struct15:<f0> -> struct16;
struct16 [label=leaf, shape=ellipse];
struct15:<f2> -> struct17;
struct17 [label=leaf, shape=ellipse];
struct15:<f4> -> struct18;
struct18 [label=leaf, shape=ellipse];
}


Deleting: 197
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 156|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> |<f3> 120|<f4> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 39|<f2> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct1:<f2> -> struct5;
struct5 [label="<f0> |<f1> 99|<f2> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct1:<f4> -> struct8;
struct8 [label="<f0> |<f1> 130|<f2> "];
struct8:<f0> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct8:<f2> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct0:<f2> -> struct11;
struct11 [label="<f0> |<f1> 172|<f2> "];
struct11:<f0> -> struct12;
struct12 [label="<f0> |<f1> 163|<f2> "];
struct12:<f0> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct12:<f2> -> struct14;
struct14 [label=leaf, shape=ellipse];
struct11:<f2> -> struct15;
struct15 [label="<f0> |<f1> 180|<f2> "];
struct15:<f0> -> struct16;
struct16 [label=leaf, shape=ellipse];
struct15:<f2> -> struct17;
struct17 [label=leaf, shape=ellipse];
}


Deleting: 156
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 130|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 39|<f2> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct1:<f2> -> struct5;
struct5 [label="<f0> |<f1> 99|<f2> |<f3> 120|<f4> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct5:<f4> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct0:<f2> -> struct9;
struct9 [label="<f0> |<f1> 172|<f2> "];
struct9:<f0> -> struct10;
struct10 [label="<f0> |<f1> 163|<f2> "];
struct10:<f0> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct10:<f2> -> struct12;
struct12 [label=leaf, shape=ellipse];
struct9:<f2> -> struct13;
struct13 [label="<f0> |<f1> 180|<f2> "];
struct13:<f0> -> struct14;
struct14 [label=leaf, shape=ellipse];
struct13:<f2> -> struct15;
struct15 [label=leaf, shape=ellipse];
}


Deleting: 99
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 130|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 49|<f2> "];
struct1:<f0> -> struct2;
struct2 [label="<f0> |<f1> 39|<f2> "];
struct2:<f0> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct2:<f2> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct1:<f2> -> struct5;
struct5 [label="<f0> |<f1> 120|<f2> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct0:<f2> -> struct8;
struct8 [label="<f0> |<f1> 172|<f2> "];
struct8:<f0> -> struct9;
struct9 [label="<f0> |<f1> 163|<f2> "];
struct9:<f0> -> struct10;
struct10 [label=leaf, shape=ellipse];
struct9:<f2> -> struct11;
struct11 [label=leaf, shape=ellipse];
struct8:<f2> -> struct12;
struct12 [label="<f0> |<f1> 180|<f2> "];
struct12:<f0> -> struct13;
struct13 [label=leaf, shape=ellipse];
struct12:<f2> -> struct14;
struct14 [label=leaf, shape=ellipse];
}


Deleting: 163
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 49|<f2> |<f3> 130|<f4> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 39|<f2> "];
struct1:<f0> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct1:<f2> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct0:<f2> -> struct4;
struct4 [label="<f0> |<f1> 120|<f2> "];
struct4:<f0> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct4:<f2> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct0:<f4> -> struct7;
struct7 [label="<f0> |<f1> 172|<f2> |<f3> 180|<f4> "];
struct7:<f0> -> struct8;
struct8 [label=leaf, shape=ellipse];
struct7:<f2> -> struct9;
struct9 [label=leaf, shape=ellipse];
struct7:<f4> -> struct10;
struct10 [label=leaf, shape=ellipse];
}


Deleting: 49
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 130|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 39|<f2> |<f3> 120|<f4> "];
struct1:<f0> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct1:<f2> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct1:<f4> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct0:<f2> -> struct5;
struct5 [label="<f0> |<f1> 172|<f2> |<f3> 180|<f4> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
struct5:<f4> -> struct8;
struct8 [label=leaf, shape=ellipse];
}


Deleting: 172
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 130|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 39|<f2> |<f3> 120|<f4> "];
struct1:<f0> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct1:<f2> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct1:<f4> -> struct4;
struct4 [label=leaf, shape=ellipse];
struct0:<f2> -> struct5;
struct5 [label="<f0> |<f1> 180|<f2> "];
struct5:<f0> -> struct6;
struct6 [label=leaf, shape=ellipse];
struct5:<f2> -> struct7;
struct7 [label=leaf, shape=ellipse];
}


Deleting: 120
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 130|<f2> "];
struct0:<f0> -> struct1;
struct1 [label="<f0> |<f1> 39|<f2> "];
struct1:<f0> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct1:<f2> -> struct3;
struct3 [label=leaf, shape=ellipse];
struct0:<f2> -> struct4;
struct4 [label="<f0> |<f1> 180|<f2> "];
struct4:<f0> -> struct5;
struct5 [label=leaf, shape=ellipse];
struct4:<f2> -> struct6;
struct6 [label=leaf, shape=ellipse];
}


Deleting: 130
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 39|<f2> |<f3> 180|<f4> "];
struct0:<f0> -> struct1;
struct1 [label=leaf, shape=ellipse];
struct0:<f2> -> struct2;
struct2 [label=leaf, shape=ellipse];
struct0:<f4> -> struct3;
struct3 [label=leaf, shape=ellipse];
}


Deleting: 180
digraph {
node [shape=record];
struct0 [label="<f0> |<f1> 39|<f2> "];
struct0:<f0> -> struct1;
struct1 [label=leaf, shape=ellipse];
struct0:<f2> -> struct2;
struct2 [label=leaf, shape=ellipse];
}


Deleting: 39
digraph {
node [shape=record];
}

```

Für diese Sequenz sind im Aufgabenblatt Graphviz-DOT-Snapshots nach vielen Einfügungen/Löschungen gezeigt. Verwende deine `dot()`-Methode, um denselben Ablauf zur Visualisierung zu erzeugen und die Struktur zu prüfen (Graphviz-Output im Template dient als Referenz, nicht als verpflichtende Textsequenz).

**Kleines Beispiel (konzeptionell, nicht vollständiger DOT-Export):**

* Nach `insert(109)` entsteht ein Knoten mit Schlüssel `109` und zwei Blatt-Kinderslots.
* Nach weiteren Inserts entstehen innere Knoten mit mehreren Schlüsseln und Blatt-Referenzen; bei Überschreitung von `b` kommt es zu Splits, wodurch die Baumhöhe ggf. wächst.

---

## Tests & Akzeptanzkriterien

Deine Implementierung sollte folgende Anforderungen in Tests erfüllen:

1. **Korrektheit**

   * `insert`, `remove`, `find`, `height` verhalten sich wie beim (a,b)-Baum.
   * `validAB(a,b)` gibt `true` für gültige Bäume und `false` für fehlerhafte Strukturen.
   * Beim Split wird das Element an `⌊b/2⌋` korrekt nach oben bewegt.

2. **Stabilität von Operationen**

   * `insert` fügt keine Duplikate ein.
   * `remove` verwendet symmetrischen Vorgänger bei Ersetzung.

3. **Logging / Debugging**

   * `dot()` produziert eine DOT-Repräsentation, die du in WebGraphviz/Graphviz ansehen kannst (die Tests erwarten keine exakte String-Form, aber die Methode ist hilfreich zum Debuggen).

4. **Performance**
   (Testangaben — halte dich an diese Limits)

   * Insgesamt etwa **300.000 Einfüge-** und **200.000 Löschoperationsläufe** innerhalb von **2 Sekunden**.
   * Nach jeder Operation wird zusätzlich noch eine Suche ausgeführt.
   * `validAB` wird nach jeweils **100.000** Operationen geprüft — die Implementierung muss also auch bei großen Sequenzen performant bleiben.

---

## Empfehlungen (praktisch, keine Lösungsanweisung)

* Achte bei Knoten-Operationen auf konstante-time Umsortierungen möglichst ohne unnötige Kopien.
* Beim Umgang mit Kindern und Schlüsseln sind sorgfältige Indexmanipulationen notwendig (off-by-one vermeiden).
* Nutze interne Hilfsfunktionen (z. B. `splitNode`, `mergeNodes`, `canStealFromLeft/Right`) zur Strukturierung des Codes — achte aber darauf, dass das API-Contract (Getter/Setter) des Templates nicht verletzt wird.
* Teste Schritt-für-Schritt mit `dot()`-Ausgaben: Einfügen einiger Schlüssel → Graphviz-Check → weitere Einfügungen/Löschungen.

---

## Randfälle, die getestet werden sollten

* Einfügen eines bereits vorhandenen Schlüssels → keine Änderung.
* Entfernen aus Blatt, innerem Knoten; Entfernen bis leerer Baum.
* Stehlen vom linken und rechten Nachbarn.
* Verschmelzen mit rechtem bzw. linkem Nachbar (wenn rechter fehlt).
* Konsistenz-Checks mit `validAB` nach vielen Operationen.
* Verhalten bei extremen Sequenzen (z. B. viele aufsteigende oder absteigende Einfügungen).
