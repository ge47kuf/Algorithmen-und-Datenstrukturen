# Binomial Heap — Die Preisverleihung

## Kurzbeschreibung

Implementiere in Java einen **Binomial Heap** für `int`-Werte. Dazu sollen zwei Klassen vervollständigt werden:

1. `BinomialTreeNode` — Knoten / Binomialbaum (Rang, Kinder, Merge zweier Bäume gleichen Ranges).
2. `BinomialHeap` — Binomialer Heap mit den Operationen `insert`, `deleteMin`, `min`, `merge` sowie Debug-/Logging-Schnittstellen.

Zusätzlich soll der Heap als Graphviz-Dot darstellbar sein (`dot`), für Debugging geeignetes Logging bereitstellen und Performance-Anforderungen erfüllen.

---

## Detaillierte Aufgabenstellung

### Allgemeines / Kontext

* Sprache: **Java** (benutze die im Template vorgegebenen Signaturen und Klassen).
* Speicher: Binomialbäume werden mittels `BinomialTreeNode` modelliert; der Heap hält eine Sammlung (z. B. Liste) dieser Wurzelbäume.
* Logging: Es gibt ein `StudentResult`/`Result`-Interface mit Methoden wie `startInsert`, `logMerge`, `logMergedTree`, `startDeleteMin` — verwende diese Hooks in der geforderten Reihenfolge (Details unter "Logging").
* Fehlerbehandlung: Ungültige Operationen (z. B. `deleteMin` auf leerem Heap) sollen eine RuntimeException (z. B. `NoSuchElementException` oder eigene Unterklasse von `RuntimeException`) werfen.

---

## BinomialTreeNode (Binomiale Bäume)

### Konstruktor

```text
BinomialTreeNode(int element)
```

* Erzeugt einen Binomialbaum vom Rang 0 mit dem übergebenen `element` als Wurzelwert.

### Getter

* `int min()`
  → Liefert das minimale Element im Teilbaum (an der Wurzel).
* `int rank()`
  → Liefert den Rang (Order) des Baumes (Anzahl der Kinderkante-Ebenen; Rang 0 → Blatt).
* `BinomialTreeNode getChildWithRank(int r)`
  → Liefert in **O(1)** das Kind dieses Knotens, das Rang `r` hat (sofern vorhanden).
  → Laufzeit O(1) ist erforderlich — die Implementierung muss dies gewährleisten.

> Hinweis: Der Typ / Struktur der Kind-Verknüpfung wird im Template vorgegeben; stelle sicher, dass `getChildWithRank` in konstanter Zeit zugreift.

### `merge`

```text
static BinomialTreeNode merge(BinomialTreeNode a, BinomialTreeNode b)
```

* Merge (vereinige) zwei Bäume gleichen Ranges zu einem Baum des nächsthöheren Rangs, ohne neue `BinomialTreeNode`-Objekte zu erstellen.
* Rückgabe: Referenz auf die Wurzel, an die der andere Baum angehängt wurde (wähle die Wurzel mit dem kleineren Wert als neue Wurzel — bei Max-/Min-Konvention siehe Template; in dieser Aufgabe gilt: Heap soll nach Mutter-Pinguin-Beispiel vom kleinsten Fisch starten → BinomialHeap ist ein Min-Heap? **Wichtig:** Nutze die Konvention, die im Template vorgegeben ist; die Tests erwarten diejenige Konvention).
* Es darf keine Neuanlage von `BinomialTreeNode` erfolgen; bestehende Knoten werden nur umverkettet.

> Zusätzlich hilfreich (nicht testverpflichtend): Methode(n) zur Iteration/Abfrage der Kinderliste(n) für Logging/Debugging.

---

## BinomialHeap

### Konstruktor

* Erstelle einen leeren BinomialHeap:

```text
BinomialHeap()
```

* Optionales Overload: `BinomialHeap(Collection<BinomialTreeNode> initialTrees)` falls im Template vorgesehen — ansonsten genügt leerer Konstruktor.

### `min()`

* Liefert das kleinste Element des gesamten Heaps (ohne zu entfernen).
* Bei leerem Heap → Verhalten entsprechend Template (z. B. Exception oder spezieller Rückgabewert).

### `insert(int element)`

* Fügt `element` in den Heap ein:

  * Erzeuge (oder nutze) einen Rang-0-Baum und merge ihn in die Struktur des Heaps.
  * Nutze `log`-Hooks:

    1. Zu Beginn: `startInsert(element, <aktuellerHeapVorher>)`.
    2. Bei jedem Merge zweier Bäume: `logMerge(treeA, treeB)`.
    3. Optional nach einem Merge: `logMergedTree(resultingTree)`.
* Nach Abschluss muss der Heap in gültiger Form vorliegen (binomialer Heap).
* Laufzeit/Performance beachten (siehe Performance).

### `deleteMin()`

* Entfernt und liefert das kleinste Element im Heap:

  * Zu Beginn: `startDeleteMin(<aktuellerHeapVorher>)`.
  * Lösche die Wurzel eines minimalen Baumes, nimm deren Kinder (in richtiger Reihenfolge) und merge diese mit dem verbleibenden Heap.
  * Bei jedem Merge: `logMerge(...)` (und optional danach `logMergedTree(...)`).
* Rückgabe: der entfernte minimale Wert.
* Leere-Heap-Fehler → geeignete RuntimeException.

### `merge(BinomialHeap other)`

* Vereint alle Bäume von `other` mit dem aktuellen Heap. `other` darf dabei verändert werden.
* Logging: bei jedem Zusammenführen zweier Bäume `logMerge(...)` (wie bei insert/deleteMin).
* Ergebnis: ein gültiger BinomialHeap mit allen Elementen beider Heaps.

### `dot(...)`

* Produziert eine Graphviz-DOT-Repräsentation des Heaps (oder einer Menge Bäume) zur Visualisierung.
* Diese Methode ist für Debugging gedacht; Verwendung im StudentResult erlaubt das Ausgeben der aktuellen Baumstruktur.

---

## Logging (genau)

Die Tests prüfen das Logging-Verhalten; das gewünschte Schema:

1. **Bei jeder Operation**:

   * `insert(x)` → rufe *zu Beginn* `startInsert(x, heapSnapshot)` auf (HeapSnapshot = aktuelle Baumfolge vor der Änderung).
   * `deleteMin()` → rufe *zu Beginn* `startDeleteMin(heapSnapshot)` auf.

2. **Beim Zusammenführen zweier binomialer Bäume** (gleiches Rank-Level):

   * Rufe `logMerge(treeA, treeB)` auf (Reihenfolge der Parameter egal).
   * Optional: rufe danach `logMergedTree(resultTree)` auf, um das Ergebnisbaum-Objekt zu protokollieren (hilfreich für Visualisierung).

3. **Hinweis zur Reihenfolge**:

   * Die Reihenfolge der Bäume in einem Heap beim Loggen ist *egal* (Tests akzeptieren unterschiedliche Reihenfolgen).
   * Achte aber darauf, dass `startInsert` / `startDeleteMin` **vor** den jeweiligen Merge-Logs gerufen werden.

---

## Beispiele (Verdeutlichung Logging)

**Insert eines Wertes `1` in Heap `[0] -> [1,(2)]`:**

```
startInsert(1, [0]->[1,(2)])
logMerge([1], [0])                // Merge Rang 0
logMergedTree([0,(1)])            // optional
logMerge([0,(1)], [1,(2)])       // Merge Rang 1
logMergedTree([0,(1),(1,(2))])   // optional
```

**DeleteMin aus Heap `[5] -> [0,(1),(1,(2))]`:**

```
startDeleteMin([5]->[0,(1),(1,(2))])
// entferne [5], merge Kinder [1] (u. a.) mit Rest
logMerge([5], [1])                 // Beispiel-Merge-Schritt
logMergedTree([1,(5)])             // optional
logMerge([1,(5)], [1,(2)])         //
logMergedTree([1,(5),(1,(2))])     // optional
```

---

## Performance

* Gesamtlast: knapp **250.000** `insert`- und `deleteMin`-Operationen sowie ca. **125.000** `min()`-Aufrufe müssen innerhalb von **2 Sekunden** ausführbar sein.
* Die Implementierung muss daher asymptotisch effiziente Operationen (amortisiert O(log n) für insert/deleteMin) und praktisch geringe Konstanten besitzen.
* Vermeide teure Allokationen in inneren Schleifen; nutze iterative / in-place Umstrukturierung der Baum-Verknüpfungen.

---

## Tests / Akzeptanzkriterien

Deine Implementierung muss folgende Punkte erfüllen:

1. **Funktionalität**

   * `BinomialTreeNode`-Konstruktor erzeugt Rang-0-Bäume korrekt.
   * `min()`/`rank()`/`getChildWithRank(...)` liefern erwartete Werte (letzteres in O(1)).
   * `merge` verknüpft zwei gleichrangige Bäume ohne Neuanlage von Knoten und liefert die korrekte Wurzel-Referenz.

2. **Heap-Operationen**

   * `insert`, `min`, `deleteMin` und `merge` verhalten sich wie bei einem Binomial Heap (binomiale Bäume pro Rang, keine Duplikate gleicher Ränge ohne Merge).
   * `deleteMin` entfernt einen Wurzelknoten mit minimalem Element und re-integriert dessen Kinder korrekt.

3. **Logging**

   * `startInsert` / `startDeleteMin` werden zu Beginn der betreffenden Operationen aufgerufen.
   * `logMerge` (und optional `logMergedTree`) wird für jeden Baum-Merge verwendet. Reihenfolge akzeptiert, aber Aufrufe müssen vorhanden sein.

4. **Fehlerbehandlung**

   * Ungültige Operationen (z. B. min/deleteMin auf leerem Heap, falls Template das verlangt) führen zu einer geeigneten RuntimeException.

5. **Performance**

   * Die oben genannten Lasten und Zeitlimits müssen realistisch gehalten werden.

---

## Randfälle (testen)

* Insert/Delete auf leerem Heap.
* Mehrfache Inserts, so dass viele Bäume unterschiedlicher Ränge entstehen (Test auf korrektes Merge-Verhalten).
* Löschen aller Elemente hintereinander (Heap wird leer).
* Merge mit leerem Heap und Merge zweier großer Heaps.
* `getChildWithRank` für nicht vorhandene Ränge (verhalten klären / Exception laut Template).

---

## Kompilieren / Ausführen (Beispiel)

Angenommen dein Projekt enthält `BinomialTreeNode.java`, `BinomialHeap.java` und eine `Main`-Klasse zum Testen:

```bash
# Kompilieren (Projekt-Root)
javac -d out $(find . -name "*.java")

# Beispiel starten (ersetze Paket/Name entsprechend)
java -cp out de.tu.uni.Main
```
