# Sortierende Heaps — N-äre Heaps & Heapsort

## Kurzbeschreibung

Erweitere den binären Heap aus der Vorlesung zu einem **n-ären (dimensionalen) Max-Heap**, der seine Werte in einem einfachen `int[]`-Array speichert. Implementiere die geforderten Operationen (Indexberechnung, Konstruktoren, `insert`, `extractMax`, `merge`) sowie die statische Methode `heapsort`, so dass das Array mit Hilfe des Heaps sortiert wird. Zusätzlich soll eine `dot`-Methode die Visualisierung des aktuellen Heap-Arrays in Graphviz-Format ermöglichen (nur Debugging-Hilfsmittel).

---

## Ziel & Rahmenbedingungen

* Der Heap ist ein **Max-Heap** (größter Wert an der Wurzel).
* **Speicherung:** Alle Werte liegen in einem `int[]`-Array (keine objektorientierte Knotenstruktur).
* **Erlaubte Attribute:** Nur primitive Basistypen (`int`, `boolean`, ...), `Result` sowie Arrays aus diesen Typen sind zulässig (Tests setzen diese Beschränkung voraus).
* **Dimension:** Anzahl Kinder je Knoten (n) wird beim Erstellen übergeben; Tests erwarten Dimensionen zwischen **2 und 9**.
* **Performanceanforderungen:** Operationen müssen großskalig performant sein (s. Abschnitt Performance).
* **Logging:** Jeder Tausch (swap) zweier Elemente im internen Array muss geloggt werden (Index-Paar an `Result`). Reihenfolge der Indices beim Loggen ist beliebig.

---

## Gewünschte Methoden / Funktionalität (Übersicht)

### 1) Indexberechnung

Implementiere Hilfsmethoden zur sauberen Indexberechnung (keine Duplikation in weiteren Methoden):

* `int getChildIndex(int childIndex, int nodeIndex)`
  → Liefert den Index des `childIndex`-ten Kindes (links = 0) des Knotens `nodeIndex`.

* `int getParentIndex(int nodeIndex)`
  → Liefert den Index des Elternknotens von `nodeIndex`.

**Formeln (0-basierter Array-Index):**

* Kinder von `nodeIndex`: Indices `n * nodeIndex + 1` bis `n * nodeIndex + n`.
  \=> `getChildIndex(childIndex,nodeIndex) = n*nodeIndex + 1 + childIndex` (mit `childIndex ∈ [0..n-1]`).
* Parent von `nodeIndex` (außer bei `nodeIndex == 0`): `getParentIndex(nodeIndex) = floor((nodeIndex - 1) / n)`.

> Hinweis: Die obigen Formeln sind die Standardformel für 0-basierte n-äre Heaps; implementiere die beiden Hilfsmethoden gemäß Template-Signaturen.
<img width="461" height="161" alt="image" src="https://github.com/user-attachments/assets/1f9ed58e-fddb-4fe3-90a3-890f8b8634cc" />


---

### 2) Konstruktoren & Getter

* Konstruktor(en) sollen einen Heap aus gegebenen Werten erzeugen. Varianten im Template:

  * `NaryHeap(int[] values, int dimension, Result result)` (oder ähnlich)
  * optional: zusätzlich `maxSize`-Parameter, der die Kapazität des internen Arrays vorgibt
* Falls `maxSize` übergeben wird: das interne Array muss diese Kapazität aufnehmen können; sonst genügt Kapazität für die übergebenen Werte.
* Ein Getter soll das interne Array (oder eine Kopie, je nach Template) zurückgeben — Tests können darauf zugreifen.
* **Performance:** Heaps mit Dimension 2–9 und **2.000.000** Elementen müssen in ≤ 1 Sekunde erstellt werden können.
<img width="221" height="161" alt="image" src="https://github.com/user-attachments/assets/ee7c02e3-39c4-44fe-b34e-f5e50da118a2" />

---

### 3) `insert(int value)`

* Fügt `value` in den Heap ein (am Ende des verwendeten Bereichs), danach „sift-up“ (Elternvergleiche) durchführen, damit Max-Heap-Eigenschaft wiederhergestellt wird.
* **Performance:** Bis zu **2.500.000** Einfügungen (für Dimensionen 2–9) müssen in ≤ 1 Sekunde möglich sein.
* Logge jeden Tausch während des Sift-Up (siehe Logging).
<img width="431" height="221" alt="image" src="https://github.com/user-attachments/assets/079c391c-e59d-4bc3-8d3d-8e2edec68262" />

---

### 4) `extractMax()`

* Entfernt den größten Wert (Wurzel), gibt ihn zurück und sorgt dafür, dass der entfernte Wert anschließend an der frei gewordenen Stelle im internen Array steht (wie in den Tests erwartet).
* Standard-Vorgehen: ersetze Wurzel durch letztes Element, verkleinere Heap-Größe, dann „sift-down“ (heapify) von der Wurzel aus.
* Jeder einzelne Tausch innerhalb des Sift-Down muss geloggt werden.
* **Performance:** Aus Heaps mit **600.000** Elementen sollen insgesamt **100.000** `extractMax` in ≤ 1 Sekunde durchgeführt werden können.
<img width="221" height="161" alt="image" src="https://github.com/user-attachments/assets/54226adb-26b5-487e-9f26-a07ee5cec101" />

---

### 5) `merge(NaryHeap other)`

* Der aufrufende Heap nimmt alle Werte des übergebenen Heaps `other` in sich auf; `other` darf dabei beliebig verändert werden.
* Da in der Vorlesung kein festes Verfahren beschrieben ist, sind verschiedene gültige Implementierungen möglich (z. B. wiederholtes `insert` aller Werte, Zusammenfügen beider Arrays + Build-Heap, ...). Tests akzeptieren alle gültigen Heaps als Ergebnis.
* **Performance:** Zwei Heaps mit Größen \~600k und 400k–800k müssen in ≤ 1 Sekunde gemerged werden können.
<img width="431" height="221" alt="image" src="https://github.com/user-attachments/assets/38ef319f-25c9-456d-8bea-68cfb7845606" />

---

### 6) `heapsort(int[] array, int dimension)` (statische Methode)

* Erstelle einen Heap mit gegebener `dimension` und nutze ihn, um `array` aufsteigend zu sortieren.
* Nach Abschluss soll das Array vollständig sortiert sein (aufsteigend).
* **Performance:**

  * Arrays mit **200.000** Elementen (Dimension 2–9) müssen in ≤ 1 Sekunde sortiert werden.
  * Ein Array mit **1.250.000** Elementen und `dimension = 4` muss in ≤ 1 Sekunde sortiert werden.
<img width="221" height="161" alt="image" src="https://github.com/user-attachments/assets/d00643e2-d91a-4d52-ae52-254385a5076c" />

---

### 7) `dot()` (Graphviz-Export, optional)

* Gibt eine String-Repräsentation des Heaps im Graphviz-Dot-Format zurück (nur für Debugging).
* Die Methode nutzt intern ein privates Attribut `dotSize`, das das Ende des genutzten Bereiches markiert (nicht prüfungsrelevant).

---

## Logging (genau)

* **Bei jedem Tausch zweier Elemente im internen Array** muss ein Log-Eintrag erzeugt werden. Welcher Index zuerst geloggt wird, ist egal.
* Verwende das im Template vorgegebene `Result`-Interface zur Übergabe der Tausch-Events (z. B. `result.addSwap(i, j)` — benutze die konkrete Signatur aus deinem Template).
* **Keine zusätzlichen** und keine andere Reihenfolge der Logs: Tests erwarten genau die protokollierten Swaps.

---

## Beispiele (illustrativ)

**Index-Layout (Dimension 2 und 3, Werte sind Indices):**

* Binär (n=2):
  Array-Indices: `[0] root, [1],[2] level1, [3],[4],[5],[6] level2, ...`

* Ternär (n=3):
  Kinder von index 0 → indices `1,2,3`; Kinder von index 1 → `4,5,6` usw.

**Insert / Extract Beispiel (n=2):**
Start mit Werten `4,1,6,7,2,3,5` ergibt initialen Heap-Array nach Heapify. Nach `insert(8), insert(3), insert(9), insert(0), insert(6)` ist das interne Array entsprechend angepasst (Max-Heap-Eigenschaft wiederhergestellt). Nach mehrfachem `extractMax()` stehen die gelöschten Werte an den freien Stellen (gerankte Reihenfolge der entfernten Werte im Array sichtbar).

---

## Tests & Akzeptanzkriterien

* **Korrektheit:** Methoden verhalten sich wie Heap-Operationen; Max-Heap-Eigenschaft wird eingehalten.
* **Indexberechnung:** `getChildIndex` und `getParentIndex` liefern korrekte Indices für alle zulässigen Eingaben.
* **Speicherung:** Nur erlaubte Feldtypen verwendet; interne Daten in `int[]`.
* **Logging:** Jeder Swap wird geloggt; Reihenfolge der Logs entspricht der Reihenfolge der Swaps.
* **Performance:** Die im Abschnitt Performance genannten Skalierungen müssen erfüllt werden.
* **Robustheit:** Randfälle (leerer Heap, ein Element, `extractMax` bis leer, `merge` mit leerem Heap, sehr große Heaps) werden korrekt behandelt.

---

## Randfälle / Testideen

* Leerer Heap: `extractMax()` → Verhalten gemäß Template (z. B. Exception oder spezielle Rückgabe).
* Single-Element Heap: `insert` und `extractMax` korrekt.
* Merge mit leerem Heap / Merge in leeren Heap.
* Sehr große Heaps (2M Elemente) zum Test der Performance und Speichereffizienz.
* `dimension` am Rand (2 und 9).
* Reihenfolge der geloggten Swaps prüfen (Konsistenz mit implementiertem Sift-Up/Sift-Down).

---

## Performance (Zusammenfassung)

* Heap-Erzeugung: **2.000.000** Elemente (Dimension 2–9) in ≤ 1 s.
* `insert`: **2.500.000** Einfügungen (Dimension 2–9) in ≤ 1 s.
* `extractMax`: aus Heaps mit 600k Elementen insgesamt **100.000** Extraktionen in ≤ 1 s.
* `merge`: Merge großer Heaps (600k mit 400k–800k) in ≤ 1 s.
* `heapsort`: Sortieren von 200k Arrays (Dimension 2–9) in ≤ 1 s; 1.25M mit dimension=4 in ≤ 1 s.

> Diese Anforderungen erfordern eine effiziente, in-place Implementierung (Vermeidung unnötiger Allokationen) und eine optimale Implementation der Heap-Operationen.

---

## Kompilieren / Ausführen (Beispiel)

Benutze das mitgelieferte Projekt-Template / Build-System. Einfache Kommandozeile:

```bash
# Kompilieren (Projekt-Root)
javac -d out $(find . -name "*.java")

# Beispielausführung (Main im Template)
java -cp out de.tu.uni.Main
```
