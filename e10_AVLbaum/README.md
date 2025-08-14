# AVL-Baum — Halbweihnachten (Präsenzaufgabe)

## Kurzbeschreibung

Implementiere in **Java** einen **AVL-Baum** (selbstbalancierender binärer Suchbaum) mit folgenden Eigenschaften (wie im Template gefordert):

* Es werden nur **Schlüssel** (`int`) gespeichert — keine assoziierten Werte.
* Mehrfach gleiche Schlüssel **erlaubt** (Dubletten). **Einfügen bei Gleichheit immer in den rechten Teilbaum.**
* **Keine Parent-Referenzen**: Knoten dürfen **keine** Zeiger auf ihren Vater haben.
* Getter/Setter, die in Tests verwendet werden, dürfen keine versteckte Logik haben, die die Tests stört.
* Implementiere Methoden: `height()`, `validAVL()`, `find(int key)`, `insert(int key)` sowie optional `dot()` für Graphviz-Dump.

Dieses README beschreibt Anforderungen, erwartetes Verhalten, Beispiele und Test-/Performance-Erwartungen.

---

## API / Annahmen (Schnittstelle)

Im Template sind zwei Klassen vorgegeben (z. B. `AvlNode` und `AvlTree` oder `Node` und `AVL`). Die exakten Signaturen im Template sind maßgeblich — passe die Namen im Code an die Vorlage an. Typische Knotenschnittstelle:

```java
class Node {
    int key;
    Node left;
    Node right;
    int balance; // expected: height(right) - height(left)
    // getters/setters vom Template
}
```

Wichtige Vorbedingungen:

* Keine `parent`-Felder in Node.
* Setter/Getters müssen simpel bleiben (keine Seiteneffekte).

---

## Erwartete Methoden & Verhalten

### `int height()`

* Liefert die **Höhe** des Teilbaums, auf dem die Methode aufgerufen wird.
* Konvention: Ein Blattknoten hat Höhe **1**. Für eine leere (null) Referenz wird üblicherweise 0 zurückgegeben (achte auf Template-Vorgaben).
* Definition rekursiv: `height(node) = 1 + max(height(node.left), height(node.right))`.

### `boolean validAVL()`

Prüft, ob der Baum ein **gültiger AVL-Baum** ist. Muss `true` zurückgeben **genau dann**, wenn alle Bedingungen erfüllt sind:

1. **Balance-Feldkorrektheit:** Für jeden Knoten `n` gilt
   `n.balance == height(n.right) - height(n.left)`
   (WICHTIG: die Reihenfolge ist genau so wie angegeben!)

2. **Balance-Range:** Für jeden Knoten `n` gilt
   `| n.balance | <= 1`

3. **BST-Eigenschaft mit Duplikaten:**

   * Alle Schlüssel im linken Teilbaum sind `<= n.key`.
   * Alle Schlüssel im rechten Teilbaum sind `>= n.key`.
     (Diese Gleichheitsregel erlaubt Duplikate — diese werden bei Einfügen auf die rechte Seite gesetzt.)

4. **Keine Zyklen:** Der Baum darf **keine Kreise** enthalten. Betrachte alle Kanten als ungerichtet (Vater→Kind) — die Struktur muss ein Wald sein (keine Wiederbesuche beim Traversieren). `validAVL()` muss Kreise erkennen und in diesem Fall `false` liefern.

Die Methode sollte alle diese Prüfungen kombinieren (rekursiv/DFS) und `true`/`false` liefern.

### `boolean find(int key)`

* Gibt `true` zurück, falls `key` im Baum vorkommt (mindestens einmal), sonst `false`.
* Nutze die BST-Eigenschaft: Links `<=`, Rechts `>=`.

### `void insert(int key)`

* Fügt `key` in den Baum ein (new node bei Blattposition).
* **Bei Gleichheit**: füge in den **rechten** Teilbaum.
* Nach Einfügen: führe AVL-Rotation(s) durch, bis der Baum wieder balanciert ist — exakt nach der Vorlesungsmethode (LL, RR, LR, RL).
* **Keine neuen Nodes** beim Balancieren erzeugen (Rotationen müssen echte Verweise umhängen).
* **Balance-Felder** aller betroffenen Knoten müssen korrekt gesetzt werden.
* Getter/Setter im Template dürfen nicht durch Insert auf eine Weise verändert werden, die Tests stört.

### `String dot()` (optional)

* Erzeuge eine Graphviz-Darstellung des aktuellen Baumes (für Debugging).
* Nicht getestet auf Genauigkeit; nützlich für eigene Visualisierung.

---

## Rotationen — Kurzreferenz (siehe Vorlesung)

Rotationen sollen so implementiert werden, dass **keine** neuen `Node`-Objekte entstehen — nur Zeigerumhänge:

* **Right Rotation (LL-Fall):**

  ```
     y                x
    / \     ->       / \
   x   C            A   y
  / \                  / \
  ```

A   B                B   C

```
Update: `y.left = x.right; x.right = y;` danach Balance-Felder aktualisieren.

- **Left Rotation (RR-Fall):**
```

x                 y
/ \    ->         /&#x20;
A   y             x   C
/ \           /&#x20;
B   C         A   B

Update: `x.right = y.left; y.left = x;` danach Balance-Felder aktualisieren.

- **LR (Left-Right) = Left Rotation on left child then Right Rotation on node.**
- **RL (Right-Left) = Right Rotation on right child then Left Rotation on node.**

Nach jeder Rotation müssen die `balance`-Felder der beteiligten Knoten korrekt berechnet werden — am sichersten durch Neuberechnung über `height(...)` (aber effizienter ist die Vorlesungsformel mit Inkrement/Decrement). Beide Varianten akzeptabel, solange Performance stimmt.

---

## Zusätzliche Anforderungen / Einschränkungen
- **Keine Parent-Referenzen**: alle Algorithmen müssen ohne parent-Zeiger arbeiten. Rückgabe/Neuzuweisung von Wurzelreferenz ggf. durch `insert`/`rotate`-Methoden sicherstellen.
- **Setter/Getters** die Tests nutzen dürfen keine Versteckten Seiteneffekte haben; setze bei ihnen keine Balancing-Logik ein.
- **Dublettenregel**: gleiche Werte immer in rechten Teilbaum einfügen (bei Insert).
- **Validierung**: `validAVL()` wird in Tests mehrfach (z. B. nach jeweils 120k Insert-Operationen) aufgerufen — stelle sicher, dass diese Methode schnell genug ist (rekursive Höhenberechnung ggf. mit Memo vermeiden, aber rechenintensivere Varianten sind bei den getesteten Größen tolerierbar, solange sie nicht stark ineffizient sind).

---

## Beispielablauf (kleiner Baum)
Start: leer  
`insert(10)`: Baum -> `[10]` (height 1, balance 0)  
`insert(5)`: `[10]` mit left 5 → balance(10) = -1  
`insert(15)`: `[10]` mit left 5 and right 15 → balance 0  
`insert(20)`: Einfügen ins rechte Subtree → bei Bedarf Rotation (RR) → Baum balancieren.  
Dubletten: `insert(10)` (nochmal) → geht in rechten Teilbaum von 10.

---

## Validierung & Tests (empfohlen)

### Unit-Tests
1. **height()**
 - Baum mit einem Knoten → `height() == 1`.
 - Baum mit left-only chain → height == Länge.
 - Test für mehrere Konstellationen.

2. **find(int)**
 - Vorhandene und nicht-vorhandene Werte.
 - Mehrfach eingefügte Werte → `find` findet sie.

3. **insert(int)**
 - Einzeln: Erzeuge Bäume, die jede Rotationsart (LL, RR, LR, RL) auslösen.
 - Reihenfolge testen: Einfügen in stetig steigender Reihenfolge → führt zu Rotationen (prüfe Höhe / balances).
 - Dubletten einfügen → immer rechts einfügen; validAVL danach `true`.

4. **validAVL()**
 - Baue absichtlich falsche Bäume (falsche balances, Verletzung der BST-Eigenschaft, Kreise durch manuelles Setzen der Kinder) und stelle sicher, dass `validAVL()` `false` meldet.
 - Normale Einfügefolgen → `validAVL()` `true`.

5. **Last Full Test**
 - Führe eine größere Serie von Inserts (z. B. 100k) und zwischendurch `validAVL()` Aufrufe (z. B. alle 10k) — vergleiche mit Referenzimplementation oder brute-force Checks.

### Performance-Tests (wie in Aufgabenblatt)
- **Insert + find**: ~500.000 Einfügungen in ≤ 2.5 s, danach Suchen (alle eingefügten + 500 nicht eingefügte) müssen schnell laufen.
- **validAVL** wird nach jeweils 120.000 Inserts getestet — muss ebenfalls performant genug sein.

Tipp: für Performance nutze iterative Höhen-/Balance-Updates bei Rotationen (statt wiederholt komplette `height`-Rekursionen zu rechnen).

---

## Fehlerbehandlung
- `insert` sollte bei `null`-Werten entsprechend Template verhalten (in Java `int` ist primitiv, `null` irrelevant).  
- `find` auf leerem Baum → `false`.  
- `validAVL` auf leerem Baum → `true` (leerer Baum ist ein valider AVL-Baum).

---

## Implementierungs-Hilfsmittel / Pseudocode (Insert mit Balancing)

**Insert (rekursiv, gibt neue Subroot zurück):**

Node insertNode(Node root, int key) {
  if (root == null) return new Node(key);

  if (key <= root.key) { // Achtung: in dieser Aufgabe gleich -> rechts; also:
     // wenn du Gleichheit auf rechts willst:
     // if (key < root.key) root.left = insertNode(root.left, key);
     // else root.right = insertNode(root.right, key);
  }
  // korrekt: 
  if (key < root.key) root.left = insertNode(root.left, key);
  else root.right = insertNode(root.right, key);

  // update balance: balance = height(right)-height(left)
  root.balance = height(root.right) - height(root.left);

  // Prüfe Balancing und rotiere falls notwendig:
  if (root.balance < -1) { // left-heavy
      if (/* left child's balance <= 0 */) {
          root = rotateRight(root); // LL
      } else {
          root.left = rotateLeft(root.left); // LR
          root = rotateRight(root);
      }
  } else if (root.balance > 1) { // right-heavy
      if (/* right child's balance >= 0 */) {
          root = rotateLeft(root); // RR
      } else {
          root.right = rotateRight(root.right); // RL
          root = rotateLeft(root);
      }
  }
  // update balances after rotations
  return root;
}

Wichtig: Beachte die **Gleichheitsregel** — "gleich → rechts". Implementiere entsprechend (`if (key < root.key) left else right`).

---

## Hinweise zu Balance-Updates

* Einfachheit: `root.balance = height(root.right) - height(root.left)` ist korrekt, aber teuer (rekursive Höhenberechnung). Für Performance empfiehlt sich:

  * Beim Rückweg der Rekursion Höhe der Kinder bekannt haben und per Formel `balance` inkrementell setzen.
  * Nach Rotation `balance`-Werte der beteiligten Knoten gemäß Vorlesungsformeln anpassen (konstante Zeit).

Wenn Performance-Probleme auftreten, optimiere das Balance-Update statt vollständige `height`-Rekursionen.

---

## Kompilieren / Ausführen (Beispiel)

Typische Befehle (Projekt-Root):

```bash
javac -d out $(find . -name "*.java")
java -cp out de.tu.uni.Main   # oder wie im Template vorgesehen
```

Nutze mitgelieferte `main()` / Testklassen im Template, um Einfüge- und Validierungsläufe auszuführen.

---

## Debugging / Visualisierung

* Implementiere `dot()` (Graphviz) falls gewünscht: In Tests nicht erforderlich, hilft beim Debuggen.
* Visualisiere z. B. mit [http://webgraphviz.com/](http://webgraphviz.com/).

