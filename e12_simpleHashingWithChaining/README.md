# Simple Hashing with Chaining

## Kurzbeschreibung

Implementiere eine einfache Hashtabelle mit **Chaining** (Verkettung) in **Java**. Das interne Array hat eine Länge, die immer eine Zweierpotenz ist. Die Hashtabelle ist eine **Multimap** — ein Key kann mehrfach mit verschiedenen Values vorkommen. Ziel der Aufgabenstellung ist es, die in der Vorlage angegebenen Methoden korrekt, robust und performant umzusetzen.

---

## Ziel & Anforderungen (Kurz)

* Sprache: **Java** (vorhandenes Projekt/Template verwenden).
* Interne Array-Größe: immer eine **Zweierpotenz** (2^k).
* Alle Buckets (Listen) sollen **bereits im Konstruktor** initialisiert werden — später dürfen in dem Array keine `null`-Einträge vorkommen.
* Hashing: byteweise Kombination des Keys mit einem vorgegebenen Vektor `a`, am Ende Reduktion auf Tabellenlänge durch eine **schnelle Modulo**-Variante (Tabellengröße = Zweierpotenz).
* Die Hashtabelle ist eine **Multimap**:

  * `insert(key, value)` fügt an das **Ende** der jeweiligen Bucket-Liste an.
  * `remove(key)` entfernt **alle** Werte für den Key und gibt zurück, ob etwas entfernt wurde.
  * `find(key)` liefert das älteste (zuerst eingefügte) Value für den Key, als `Optional<T>`.
  * `findAll(key)` liefert alle Values in Einfügereihenfolge als `List<T>`.

---

## Vorgegebene / zu implementierende Komponenten (API)

> Passe die Signaturen an das Template an, falls die Projektschablone andere Namen verwendet. Unten sind die Konzepte und erwarteten Semantiken beschrieben.

### Hilfsfunktion

* `int getNextPowerOfTwo(int v)`
  Gibt die kleinste Zweierpotenz `>= v` zurück. Eingaben sind nicht-negativ.

### Konstruktor

* `Hashtable(int minSize, int[] a)`

  * `minSize`: gewünschte minimale Kapazität — die tatsächliche Länge des internen Arrays ist `getNextPowerOfTwo(minSize)`.
  * `a`: Vektor für die Hashfunktion (wird in `h` verwendet). In der Vorlage ist `a` ein `int[]`, wobei jedes Element ein Byte-Wert repräsentiert.

> Im Konstruktor müssen **alle** Buckets (z. B. `ArrayList`/`LinkedList`) sofort angelegt werden.

### Schnelles Modulo

* `int fastModulo(int value, int divisor)`

  * Erwartet, dass `divisor` eine Zweierpotenz ist.
  * Soll ein `value mod divisor`-Äquivalent zurückgeben, so dass das Ergebnis im Bereich `[0, divisor)` liegt. Für negative `value` ist kein mathematisch exaktes Ergebnis gefordert, aber der Rückgabewert muss für Indexbildung brauchbar sein.

> Es existiert außerdem ein `ModuloHelper`-Interface im Template. Die Tests können eine Implementierung davon übergeben; du kannst das Interface nutzen oder deine eigene Variante implementieren.

### Hashfunktion

* `int h(byte[] keyBytes)` oder `int h(K key)` — abhängig vom Template:

  * Die Hashfunktion multipliziert **byteweise** die Bytes des Keys mit den jeweiligen Einträgen von `a` (zyklisch, falls `a` kürzer ist) und summiert die Produkte auf.
  * **Erst nachdem** die Summe gebildet wurde, wird das Ergebnis mittels `fastModulo(..., tableSize)` auf `[0, tableSize)` reduziert.
  * Das Ergebnis ist der Index/Bucket in der Tabelle.

**Beispiel (Illustration):**
Key `"Pinguin"` → Bytes `[80, 105, 110, 103, 117, 105, 110]`
Vektor `a = [1, 42, 69]` und `tableSize = 128`
Rechnung (ohne Modulo): `80*1 + 105*42 + 110*69 + 103*1 + 117*42 + 105*69 + 110*1 = 24452`
Index = `24452 % 128 = 4` (im Template-Beispiel).

### Einfügen / Entfernen / Suchen

* `void insert(K key, V value)`

  * Fügt `(key,value)` in die Hashtabelle ein. Mehrere gleiche Keys sind erlaubt. Neuer Eintrag wird ans **Ende** der Liste im entsprechenden Bucket angehängt.

* `boolean remove(K key)`

  * Entfernt **alle** Werte mit diesem Key. Gibt `true` zurück, wenn mindestens ein Eintrag entfernt wurde, sonst `false`.

* `Optional<V> find(K key)`

  * Liefert das **älteste** Value für `key` als `Optional` (oder `Optional.empty()` falls keines vorhanden).

* `List<V> findAll(K key)`

  * Liefert **alle** Values für `key` in Einfügereihenfolge als `List<V>` (leere Liste falls nichts gefunden).

> Hinweis: Das Template stellt bereits ein `Pair`-Record für Key/Value zur Verfügung — dieses musst du nicht ändern.

---

## Verhalten / Randfälle

* Keys/Values können `null` sein oder nicht? Verwende die Vorgaben aus dem Template. Falls nichts angegeben, erwarte keine `null`-Keys; aber methodisch sollte dein Code `null` nicht ungeprüft zum Absturz führen (ausreichende Dokumentation ist wichtig).
* Negative Hash-Intermediärwerte: `fastModulo` muss brauchbar sein (Index in Bereich `[0, size)`).
* Bucket-Implementierung: beliebig (z. B. `LinkedList`, `ArrayDeque`, `ArrayList`), solange Einfügereihenfolge erhalten bleibt und Operationen performant sind.

---

## Beispiele (erwartetes Verhalten)

1. **Initialisierung**

   ```java
   Hashtable<String, Integer> ht = new Hashtable<>(100, a);
   // interne Größe z. B. 128 (kleinste Zweierpotenz >= 100)
   ```

2. **Insert / Find**

   ```text
   insert("A", 1)
   insert("B", 2)
   insert("A", 3)
   find("A")   -> Optional.of(1)     // ältester Wert für "A"
   findAll("A")-> [1, 3]
   ```

3. **Remove**

   ```text
   remove("A") -> true
   findAll("A")-> []                 // alle Einträge für "A" entfernt
   remove("X") -> false              // nichts entfernt
   ```

4. **Hash-Index (Illustration)**

   * `"Pinguin"` mit `a=[1,42,69]` und `tableSize=128` → Index `4` (siehe obiges Rechenbeispiel).

---

## Performance & Tests (erwartete Größenordnungen)

Die Tests prüfen sowohl Korrektheit als auch Laufzeit. Implementiere deine Methoden effizient:

* **Kleine Tabelle** (`tableSize = 128`)

  * ca. **275k Inserts**, **225k Removes**, insgesamt \~**500k** `find`/`findAll`-Aufrufe in **2 Sekunden**.

* **Große Tabelle** (`tableSize = 2_097_152`)

  * ca. **825k Inserts**, **675k Removes**, insgesamt \~**800k** `find`/`findAll`-Aufrufe in **2 Sekunden**.

* **fastModulo** soll extrem schnell sein: diese Hilfsfunktion wird sehr oft aufgerufen (in Tests in großem Umfang). Implementiere sie so, dass sie die Anforderungen erfüllt (die Tests messen die Gesamtperformance der Hashtable).

**Komplexitätserwartung**

* Insert / Remove / Find (amortisiert): O(1) erwarteter Zeit, abhängig von der Lastfaktor-Verteilung des Hashes.
* `findAll` liefert eine Liste, deren Laufzeit O(k) ist, wobei k die Anzahl der Einträge im Bucket für den Key ist.

---

## Testhinweise

* Verwende die mitgelieferten Unit-Tests im Template (falls vorhanden).
* Testfälle, die du selbst probieren solltest:

  * Einfügen vieler Schlüssel, danach `find` und `findAll` prüfen.
  * Entfernen eines Keys mit mehreren Values (alle müssen gelöscht werden).
  * Verhalten bei Keys, die denselben Bucket treffen (Kollisionsfälle).
  * Randfälle: `minSize` bereits Zweierpotenz, `minSize = 0` oder `1`, sehr große `minSize`.
  * `getNextPowerOfTwo` mit Randwerten (`0`, `1`, `2`, `3`, `2^k`).

---

## Hinweise zur Implementierung (ohne Lösungsdetails)

* Achte darauf, dass **keine Bucket-Einträge `null`** werden — Buckets sollen durchgehend initialisiert sein.
* Vermeide unnötige Allokationen in inneren Schleifen (vor allem bei großen Testläufen).
* Die Hashfunktion verwendet den Vektor `a` zyklisch, falls `a.length < keyBytes.length` — die Summe soll erst vollständig gebildet werden und erst dann auf Tabellenlänge reduziert werden.
* Nutze vorhandene Datentypen aus dem Template (z. B. das `Pair`-Record) statt neue Entitäten zu erfinden.

---

## Mögliche Stolperfallen

* `fastModulo` muss für Divisoren, die Zweierpotenzen sind, korrekt in `[0, divisor)` liefern — negative Zwischenwerte sollten gehandhabt werden.
* Beim Entfernen aller Einträge eines Keys ist sicherzustellen, dass die dahinterliegenden Bucket-Listen danach in korrektem Zustand bleiben.
* `find` muss das älteste Element zurückgeben — das Verhalten der Liste (FIFO vs. LIFO) ist hier entscheidend (neu eingefügte Werte ans Ende).

---

## Zusammenfassung

Setze die vorgegebenen Methoden konsequent gemäß Spezifikation um, achte auf die Einfügereihenfolge in den Buckets (alte Werte sollen bei `find` zuerst zurückgegeben werden), initialisiere alle Buckets im Konstruktor und implementiere eine performante `fastModulo`-Variante. Teste deine Implementierung mit zufälligen und adversarialen Sequenzen, um sowohl Korrektheit als auch die geforderte Performance sicherzustellen.
