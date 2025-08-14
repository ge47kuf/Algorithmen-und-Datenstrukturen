# Double Hashing

## Kurzbeschreibung

Implementiere in **Java** eine Hashtabelle mit **double hashing** (offenes Adressieren). Die Aufgabe besteht aus drei Teilen:

1. `DoubleHashInt` — liefert zwei unabhängige Hashfunktionen `h(x)` und `h'(x)` für `Integer`-Keys.
2. `DoubleHashString` — liefert zwei unabhängige Hashfunktionen `h(x)` und `h'(x)` für `String`-Keys.
3. `DoubleHashTable` — die eigentliche Hashtabelle mit doppeltem Hashing, welche `h(x,i) = (h(x) + i * h'(x)) mod m` verwendet. `m` ist eine Primzahl (Tabellengröße). Die Tabelle unterstützt `insert` und `find` sowie Laufzeit-/Statistik-Queries (`collisions`, `maxRehashes`).

> **Wichtig:** Verwende **nicht** die Standard-`hashCode()`-Funktion von Java für diese Aufgabe. Die Tests erwarten eigene, deterministische Hashfunktionen. Es ist erlaubt, bewährte Ideen (z. B. multiplikative Kombinationen, große Primfaktoren, Byte- oder Wortmischungen) zu verwenden — aber keine Java-Builtin-Hashfunktionen.

---

# Anforderungen & Verhalten (Kurz)

* `DoubleHashInt` und `DoubleHashString` müssen jeweils zwei Funktionen liefern:

  * `int h(T x)` — primärer Hash (liefert Werte in einem geeigneten Wertebereich).
  * `int hTick(T x)` — sekundärer Hash (darf **0** nicht zurückgeben, sonst bleibt Schrittgröße 0); muss von `h` weitgehend unabhängig sein.
* `DoubleHashTable`:

  * Konstruktor erhält die Tabellengröße `m` (eine Primzahl) und eine Factory/Provider, die für Keys die passende DoubleHash-Instanz liefert.
  * `hash(key, i)` implementiert: `(h(key) + i * hTick(key)) mod m`.
  * `insert(key, value)`:

    * Fügt `(key,value)` ein; falls Key bereits vorhanden → **überschreiben** (update).
    * Bei erfolgreichem Einfügen eines neuen Paares soll `true` zurückgegeben werden; andernfalls `false` (z. B. Tabelle voll).
    * Zähle Kollisionen / Rehashes (siehe unten).
  * `find(key)`:

    * Gibt `Optional<V>` zurück (Optional.empty() falls nicht vorhanden).
  * `collisions()`:

    * Anzahl der Einträge in der Tabelle, die **nicht** auf ihrem bevorzugten Platz stehen (d. h. ihr gespeicherter Index ≠ `h(key) mod m`). Muss in **O(1)** zurückgegeben werden.
  * `maxRehashes()`:

    * Das Maximum der bei bisherigen Insert-Operationen benötigten Rehash-Versuche `i` (also höchster `i` ≥ 1, der benötigt wurde). Muss in **O(1)** zurückgegeben werden.

---

# Detaillierte Spezifikation

## DoubleHashInt

* Zweck: berechne zwei unabhängige Hashfunktionen auf Integern.
* Anforderungen:

  * `h(int x)` und `hTick(int x)` sollten Werte gleichverteilt über `0..m-1` (oder geeignet für Tabelle) liefern, wenn viele verschiedene Integer gehasht werden.
  * `h` und `hTick` sollen **unabhängig** sein: gleiche `h`-Werte für zwei Keys sollten nicht systematisch gleiche `hTick`-Werte erzeugen.
  * `hTick` darf nicht 0 modulo m sein (ansonsten kein Fortschritt beim Rehashing). In der Tabelle kann man `hTick % m` verwenden und falls 0 wird normalerweise `hTick = 1` oder ähnliches gewählt — stelle sicher, dass Schrittgröße für die Tabelle immer > 0 ist.
* Performance: Muss sehr schnell sein — Millionen Aufrufe pro Sekunde möglich.

## DoubleHashString

* Zweck: berechne zwei unabhängige Hashfunktionen auf Strings.
* Anforderungen:

  * Muss mit zufälligen Strings **und** mit realen Wortlisten (z. B. häufige deutsche/englische Wörter) gute Verteilung zeigen.
  * `h` und `hTick` sollten möglichst unabhängig sein.
  * `hTick` darf nicht 0 (mod m) sein; sorge für sichere Behandlung.
* Tips (nicht zwingend Regeln):

  * Arbeite byte-/char-orientiert, nutze große Primfaktoren/Multiplikatoren, mische unterschiedlich für `h` und `hTick`.
  * Die beiden Hashfunktionen sollten nicht nur triviale Variationen voneinander sein.

## DoubleHashTable

* Konstruktor:

  * Parameter: `int m`, `DoubleHashFactory<T>` (oder ähnliches), je nach Template.
  * `m` ist eine Primzahl (wird in Tests so übergeben). Verwende `m` für Modulo-Rechnen.
* Hash-Formel (probenweise):

  * `h(x, i) = (h(x) + i * hTick(x)) % m`
  * Verwende `i = 0,1,2,...` bis freier Slot gefunden wird oder Tabelle voll.
* Insert-Regeln:

  * Falls Key bereits in der Tabelle (gefunden via probing) → ersetze den zugehörigen Wert (update).
  * Falls ein leerer Slot gefunden → setze Pair(key,value) dort ein.
  * Wenn während Insert der Slot `j` für den neuen Key nicht gleich `h(key)` ist, dann zählt dieser Eintrag später als Kollision (kann sofort inkrementiert werden; siehe Collisions-Definition).
  * Tracke die Anzahl der **Rehash-Versuche** (`i`) nötig, und aktualisiere `maxRehashes`.
  * Tracke Kollisionen: jedes Element, das nicht auf seiner `h(key)`-Position liegt, zählt als 1 Kollision (oder je Task-Definition).
* Find-Regeln:

  * Suche mittels derselben Probing-Sequenz `i = 0..` bis Key gefunden oder ein leerer Slot entdeckt → `Optional.empty()`.
* Statistik-APIs:

  * `collisions()` sollte die aktuelle Anzahl der Kollisionen (Anzahl der Elemente nicht auf ihrem bevorzugten Index) zurückgeben — **O(1)**.
  * `maxRehashes()` sollte das bisher höchste `i` zurückgeben, das beim Insert eines Elements benötigt wurde (i≥1 zählt als Rehash). **O(1)**.

---

# Beispiele

> Diese Beispiele zeigen das *Verhalten* (keine Implementationshinweise).

**Tabelle mit `m = 11` (Primzahl)** (nur illustrativ):

* Keys: `10, 21, 32`

  * Angenommen `h(10)=10, hTick(10)=3`. Dann Plätze für `10` versuchen: 10, (10+1*3)%11=2, (10+2*3)%11=5, ...
  * Wenn 10 belegt, wird weiter mit `i=1` usw. geprobt.

**Insert / Update:**

* `insert("foo", 1)` → findet freien Slot nach k Probes → `true`.
* `insert("foo", 2)` → Key existiert bereits (bei Probe gefunden) → aktualisiere Wert → `true` (update).
* `find("foo")` → `Optional.of(2)`.

**Kollisionen:**

* Wenn Element `e` bei Index `j` gespeichert wird und `j != h(e)`, dann zählt `e` als 1 Kollision (Element befindet sich nicht auf bevorzugtem Platz). Summiere über alle Tabelleinträge für `collisions()`.

**maxRehashes:**

* Wenn beim Einfügen von `x1` `i` maximal 3 erreicht wurde, und später für `x2` nur 2, so bleibt `maxRehashes = 3`.

---

# Randfälle & Anforderungen

* `hTick(x) % m` darf nicht 0; sorge für Fallback (z. B. +1 / mappe 0→1) — damit der Schritt nicht 0 wird.
* Bei voller Tabelle (keine freien Slots) soll `insert` fehlschlagen (je Template: Rückgabewert oder Exception — beachte Vorlage).
* `find` muss korrekt `Optional.empty()` liefern, wenn Key nicht vorhanden ist.
* `collisions()` & `maxRehashes()` müssen jederzeit in O(1) zurückgegeben werden.
* Hashfunktionen dürfen deterministisch sein; Entropie in Tests kommt durch Keysets.

---

# Performance (Erwartungen)

* **Hashing-Performance**

  * `DoubleHashInt` (Int-Hashes): mehrere Millionen Hashes pro Sekunde erwartet.
  * `DoubleHashString`: \~500.000 Hashes pro Sekunde ausreichend (Strings sind teurer).
* **Tabelle**

  * Beispiel-Test: Tabelle mit `m ≈ 50.000`:

    * ca. **7.000 Inserts** und ca. **50.000.000 Finds** in **≤ 2s**.
  * Die Statistik-Abfragen (`collisions`, `maxRehashes`) müssen konstant schnell sein.

---

# Testempfehlungen (Unit-Tests & Benchmarks)

**Funktionalitätstests**

* Kleine Tabellen:

  * Insert mehrere Keys, provoziere Kollisionen (z. B. gezielt Keys mit gleichen `h`-Werten), prüfe `find` & `insert` (update).
  * Prüfe `collisions()` nach Einfügungen (vergleiche erwartete Anzahl nicht-auf-prefered-Positionen).
  * Prüfe `maxRehashes()` nach Sequenz, in der einige Keys mehrere Probes benötigen.
* Edge-Cases:

  * Insert bis Tabelle voll → Insert liefert erwartetes Verhalten (false/exception).
  * `hTick` zurückgibt 0 (wenn unbeabsichtigt) → stelle sicher, dass Schrittgröße nie 0 ist.
* Strings:

  * Test mit zufälligen Strings + häufigen Wortlisten (z. B. 1000 häufige deutsche/englische Wörter).
  * Verteile Keys so, dass `h` teilweise kollidiert; `hTick` sollte trotzdem unterscheiden.

**Performance-Tests**

* Microbenchmarks für `h` und `hTick`:

  * Viele zufällige Integer/Strings hashen und Messung der Hashes/s.
* Große Lasttest für Tabelle:

  * Erzeuge große Anzahl von `find`-Operationen (z. B. 50M) gegen Tabelle mit \~50k Einträgen und messe Dauer.
* Stats-Check:

  * Nach Insert-Phase verifiziere `collisions()` und `maxRehashes()` konstantes Laufzeitverhalten.

---

# Hinweise & Do’s / Don’ts

* **Do:** Sorge dafür, dass `h` und `hTick` unabhängig genug sind — gleiche `h`-Werte sollten nicht zu gleichen `hTick`-Werten führen.
* **Do:** Mache `hTick % m` nicht 0 (Fallback).
* **Do:** Tracke Kollisionen und maxRehashes während der `insert`-Operationen (am billigsten ist es beim finalen Platzieren / Update eines Slots aufzubrechen und die Statistik zu aktualisieren).
* **Don’t:** Verwende nicht `Object.hashCode()`/`String.hashCode()` als einzige Hashquelle — die Aufgabe verlangt eigene Hashfunktionen.
* **Don’t:** Führe teure Synchronisations- oder IO-Operationen in den heißen Pfaden (`hash`, `insert`, `find`) aus — Tests setzen strenge Laufzeitlimits.
