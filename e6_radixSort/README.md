# Radixsort

## Kurzbeschreibung

Implementiere in **Java** mehrere Varianten von RadixSort:

1. **Dezimaler RadixSort** für nicht-negative Dezimalzahlen — es fehlen zwei Hilfsmethoden (`key` und `getMaxDecimalPlaces`).
2. **Binärer RadixSort** (arbeitet auf den Binärbits, kann negative Zahlen behandeln). Dazu musst du einen `BinaryBucket` implementieren und die Methoden `key`, `kSort`, `sort` und `lastSort` für `BinaryRadixSort` ergänzen.

Wichtig: **keine String-Umwandlungen** zur Extraktion von Ziffern — nur arithmetische / bitweise Operationen!

---

## Detaillierte Aufgabenstellung

### Allgemeine Hinweise

* Sprache: **Java** (verwende exakt die Signaturen im Template).
* Dezimaler Teil sortiert nur **nicht-negative** Dezimalzahlen (≥ 0).
* Binärer Teil sortiert **ganze `int`-Werte** (inkl. negativer Zahlen) über ihre 32-Bit-Binärdarstellung.
* Performanceanforderung für binäre Variante: **\~2.500.000 ints** in **≤ 2 Sekunden**.
* Logging: Falls das Template Logging erwartet, halte dich an das vorgegebene Logging-Verhalten (keine zusätzlichen Logs).

---

## Teil A — Dezimaler RadixSort (vorhandener Code + Ergänzungen)

Im Template ist ein funktionierender Rahmen für den dezimalen RadixSort enthalten, jedoch fehlen zwei Methoden:

### `int key(int value, int i)`

* Gibt die Dezimalziffer von `value` an Stelle `i` (gezählt von hinten, `i = 0` → Einerstelle) zurück.
* Formal: Für `value = d_n ... d_1 d_0` gilt `key(value, i) = d_i` falls `i <= n`, sonst `0`.
* **Verbot:** Keine String-Operationen. Benutze arithmetische Operationen (z. B. Division / Modulo mit Potenzen von 10).

> Tipp (Kurzformel): `key(value,i) = (value / 10^i) % 10` — implementiere Potenzen / Schleife / schnelles Rechnen ohne Strings.

### `int getMaxDecimalPlaces(int[] arr)`

* Liefert die Anzahl der Dezimalstellen der größten Zahl im Array (z. B. `0 -> 1`, `9 ->1`, `10 -> 2`).
* Rückgabe ist die maximale Stellenanzahl aller Elemente; nutze das, um unnötige Stellenläufe zu vermeiden.
* **Verbot:** Keine String-Umwandlungen. Bestimme Stellen per Division durch 10 in einer Schleife.

---

## Teil B — Binärer RadixSort

Die binäre Variante sortiert `int[]` (inkl. negativer Werte) über **32 Bit** (LSB → MSB oder MSB→LSB je nach Template-Vorgabe; übliche Implementierung läuft von LSB (bit 0) nach MSB (bit 31)). Im Template sind Teile vorgegeben; du musst ergänzen:

### BinaryBucket (Wrapper für Zielarray)

`BinaryBucket` ist ein Hilfsobjekt, das ein Array der gegebenen Größe verwaltet und zwei Einfüge-Richtungen unterstützt:

Erwartete Funktionalität (Signaturen / Verhalten)

* `public BinaryBucket(int size)` — erstellt internen Puffer `int[size]`.
* `public void insertLeft(int value)` — fügt `value` an der nächsten freien Position von links ein (wie push auf linken Stack).
* `public void insertRight(int value)` — fügt `value` an der nächsten freien Position von rechts ein (wie push auf rechten Stack).
* `public int getMid()` — gibt den Index zurück, an dem die von links eingefügten Werte enden (erste nicht-belegte Position nach links). Damit weiß man, ab wo die rechten Werte stehen.
* (Optional) Weitere Hilfs-Methoden intern, z. B. `toArray()` oder Zugriff auf internen Buffer, wenn das Template das erwartet.

Verhalten: `insertLeft` und `insertRight` füllen das Array von beiden Seiten wie zwei Stacks. Example (Größe 7):

```
start: [x,x,x,x,x,x,x]
insertLeft(42) -> [42,x,x,x,x,x,x]
insertLeft(1337)->[42,1337,x,x,x,x,x]
insertRight(69)->[42,1337,x,x,x,x,69]
...
getMid() -> erster Index, der nicht von Left belegt ist
```

### `BinaryRadixSort.key(int value, int binPlace)`

* Liefert das Bit (0 oder 1) von `value` an der Stelle `binPlace` zurück (binPlace = 0 → LSB). Verwende bitweise Operationen, z. B. `(value >>> binPlace) & 1`.
* Achte auf Java's Vorzeichenoperationen: zur Extraktion der reinen Bitmuster eignet sich `>>>` (logischer Rechtsshift).

### `kSort(BinaryBucket from, BinaryBucket to, int binPlace)`

* Führt **einen** Sortierschritt für das Bit `binPlace` aus:

  * `from` enthält die Zahlen in der Reihenfolge, wie sie vom vorherigen Schritt ausgegeben wurden.
  * Iteriere über `from` in der in deinem Template definierten Richtung (es sind zwei Varianten möglich — von vorne nach hinten oder von hinten nach vorne; halte dich an die Template-Vorgaben bzw. entscheide konsistent). **Wichtig:** Folge genau der Reihenfolge, die nötig ist, damit die Stabilität erhalten bleibt.
  * Wenn das betrachtete Bit = `0` → `insertLeft(value)` in `to` (fülle links nach rechts).
  * Wenn das Bit = `1` → `insertRight(value)` in `to` (fülle rechts nach links).
* Nach Abschluss enthält `to` alle Elemente so verteilt, dass der nächste kSort mit `to` als `from` weiterarbeiten kann.

Hinweis: Da es nur zwei Buckets benötigt (from/to), kannst du für iteratives Sortieren zwei `BinaryBucket`-Instanzen abwechselnd verwenden, ohne teure Listen-Konkatenationen.

### `sort(int[] array)`

* Organisiere zwei Buckets (mit Größe `array.length`), kopiere initial die Zahlen in den `from`-Bucket (oder nutze `array` als initiales `from`, abhängig vom Template).
* Führe `kSort` für jede binäre Position aus (i = 0..31). Nach jedem `kSort` (Schritt) soll das Array / aktueller Zwischenzustand geloggt werden (falls Template Logging erwartet).
* Wechsle jeweils zwischen den beiden Buckets als `from` und `to`.
* Nach den 32 Schritten ist das Array **nahezu** korrekt sortiert, allerdings müssen negative Zahlen (die in 2er-Komplement-Darstellung MSB=1 haben) noch korrekt in die endgültige Reihenfolge gebracht werden — das erledigt `lastSort`.

### `lastSort(...)`

* Nach Anwendung aller 32 `kSort`-Schritte liegen die Zahlen in den Buckets in einer Ordnung, die die Bitweise-Sortierung widerspiegelt, aber negative Werte (MSB=1) können am falschen Ende liegen (je nach Reihenfolge).
* `lastSort` überführt die Werte final ins Ausgangsarray in korrekter aufsteigender Reihenfolge (unter Berücksichtigung von Vorzeichen).
* Überlege dir, wie sich die Reihenfolge nach 32 Durchläufen darstellt: meist sind alle Nicht-Negativen / Negativen blockweise getrennt und du kannst sie aus dem Bucket in der richtigen Reihenfolge ins Ziel kopieren.

> Beispielablauf (beide Varianten sind zulässig, wähle eine konsistente): in der Aufgabenbeschreibung sind zwei Beispielsequenzen (von hinten→vorne oder vorne→hinten) gegeben — beide führen am Ende zum korrekten Ergebnis, wenn `lastSort` entsprechend umgesetzt ist.

---

## Wichtige Einschränkungen / Verbote

* **Keine String-Operationen** zur Extraktion von Dezimal- oder Binärziffern.
* Binärer RadixSort muss negative Zahlen korrekt behandeln (Nutze bitweise-Operationen und `>>>` falls nötig).
* Speicher: Verwende die zwei Buckets (Arrays) als Zielpuffer; vermeide unnötige zusätzliche große Strukturen.
* Zeit: binärer Sort soll für große Inputs performant implementiert sein (siehe Performance).

---

## Beispiele (aus der Aufgabenbeschreibung)

**BinaryBucket Beispiel**

```
start: [x,x,x,x,x,x,x]
insertLeft(42): [42,x,x,x,x,x,x]
insertLeft(1337): [42,1337,x,x,x,x,x]
insertRight(69): [42,1337,x,x,x,x,69]
insertLeft(420): [42,1337,420,x,x,x,69]
insertRight(0): [42,1337,420,x,x,0,69]
insertRight(-5):[42,1337,420,x,-5,0,69]
getMid() -> 3
```

**Binäre Sort-Schritte (Beispielsequenz)**

* Start (4-bit Darstellung für Lesbarkeit):
  `[0100, 1001, 1111, 1010, 0001, 0110]`
* Nach kSort(bit0): je nach gewählter Durchlaufvariante eine der im Aufgabenblatt gezeigten Sequenzen
* Nach mehreren kSorts (je Bit) nähert sich die Reihenfolge dem endgültigen sortierten Array. `lastSort` setzt endgültig die korrekte Reihenfolge, besonders bzgl. negativer Werte.

---

## Tests / Akzeptanzkriterien

* **Dezimaler Teil**

  * `key(value,i)` gibt korrekte Dezimalziffer zurück (inklusive `0` wenn `i` zu groß).
  * `getMaxDecimalPlaces` liefert für jede Eingabe korrekte maximale Stellenanzahl.

* **Binärer Teil**

  * `BinaryBucket`: `insertLeft`, `insertRight`, `getMid` arbeiten korrekt, Füllung wie zwei Stacks von gegenüberliegenden Seiten.
  * `kSort` verteilt `0`-Bits nach links und `1`-Bits nach rechts in `to` in der richtigen Reihenfolge (stabilität bewahren).
  * `sort` wendet `kSort` für alle 32 Bits an (Reihenfolge der Bits gemäß Template).
  * `lastSort` bringt negatives/positives korrekt in die finale aufsteigende Reihenfolge.
  * Komplettsortierung: `BinaryRadixSort.sort(array)` liefert vollständig sortiertes Array (aufsteigend), inklusive negativer Zahlen.

* **Performance**

  * Binärsort darf **2.500.000 ints** in **\~2 Sekunden** sortieren (effiziente Implementierung ohne unnötige Kopien oder synchronisationsintensive Operationen).

* **Dokumentation / Logging**

  * Falls das Template Logging erwartet (z. B. nach jedem Schritt), dann sind diese Logs vorhanden und in der korrekten Reihenfolge.

---

## Randfälle (zu prüfen)

* `value = 0` im Dezimal-Key und `getMaxDecimalPlaces` für Arrays nur mit 0.
* Dezimal-`key` mit sehr großen `i` → 0 zurückgeben.
* Binärsort: negatives und positives Zahlenmischung, alle Zahlen gleich, viele Nullen/Einsern in Bits.
* Sehr große Arrays (Performance & Speicherverhalten).
* Leere Arrays / Arrays mit zwei Elementen (kleinste funktionsfähige Fälle).

---

## Hinweise zur Implementierung (keine vollständige Lösung, kurze Hinweise)

* Dezimal-`key`: iterative Division durch 10 oder Multiplikation mit Potenzen von 10; bestimme `10^i` ohne Strings.
* `getMaxDecimalPlaces`: finde größtes Element `max`, zähle `while (max > 0) { max/=10; count++; }` (behandle `max==0` → 1).
* Binär-`key`: `(value >>> binPlace) & 1` (logischer Shift für signierte ints).
* `BinaryBucket`: intern 2 Indices `leftPos` und `rightPos` (z. B. `left=0`, `right=size-1`) und Array `int[] buffer`.
* `kSort`: iteriere `from` in der vom Template vorgesehenen Richtung; ruf für jedes Element `key` auf und je nach 0/1 `insertLeft/Right` auf `to`.
* `sort`: alterniere `from`/`to` über 32 Durchläufe; nach jedem kSort ggf. Logging (falls gefordert).
* `lastSort`: analysiere die Verteilung der Zahlen im Bucket nach 32 Durchläufen (häufig sind N ≥ 0 entweder links oder rechts) und kopiere dann in der richtigen Reihenfolge zurück ins Originalarray.

---

## Kompilieren / Ausführen (Beispiel)

Benutze das Projekt-Template; übliche Kommandos:

```bash
# Kompilieren (Projekt-Root)
javac -d out $(find . -name "*.java")

# Beispielausführung (Template Main)
java -cp out de.tu.uni.Main
```
