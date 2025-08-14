# Dynamic Array

## Kurzbeschreibung
Auf Basis eines dynamischen Arrays sollen verschiedene Datenstrukturen implementiert werden: ein `DynamicArray` mit automatischer Größenverwaltung (reportUsage), ein `DynamicStack` (Stack auf DynamicArray), eine zyklische `RingQueue` (Ringpuffer auf DynamicArray) und eine `StackyQueue` (Queue implementiert mit zwei Stacks). Außerdem gelten besondere Anforderungen an Logging, Speicherverhalten und erlaubte Zusatzattribute.

> Hinweis: Verwende die im Übungs-Template vorgegebenen Klassen/Interfaces (z. B. `Interval`, `NonEmptyInterval`, `EmptyInterval`, `Collection`, `Queue`, `Stack` und ggf. `Result`). In diesem README werden die Aufgaben und das erwartete Verhalten beschrieben — **keine** Lösungsschritte.

---

## Allgemeines / Vorbedingungen
- Sprache: **Java** (Benutze die genauen Signaturen aus dem Template).
- Die Klassen `Interval`, `NonEmptyInterval` und `EmptyInterval` sind bereits gegeben. Wichtig: Bei nicht-leeren Intervallen (`NonEmptyInterval`) kann `from > to` vorkommen — das Intervall läuft dann bis zum Ende des Arrays und von vorne weiter bis `to` (Wraparound).
- Nur eine begrenzte Anzahl zusätzlicher Felder ist erlaubt: in den Klassen `RingQueue` und `DynamicStack` dürfen zusätzlich `int`-Attribute und `Interval`-Attribute hinzugefügt werden (siehe Template / Aufgabenbeschreibung). Vermeide unnötige Attribute.
- Logging: In Teilaufgaben 2–4 (Stack/Queues) wird im Konstruktor ein `Result` übergeben; jede Methode, die das interne `DynamicArray` verändert, muss am Ende der Methode das aktuelle Array an das Result loggen (es gibt dafür im `DynamicArray` eine Hilfsmethode). `StackyQueue` selbst muss nichts zusätzliches loggen — es reicht, dass die verwendeten Stacks ihr Array loggen.

---

## Teil 1 — DynamicArray

### Aufgabe (Übersicht)
Implementiere die vorgegebenen Methoden der Klasse `DynamicArray`. Zusätzliche Hilfsmethoden sind erlaubt, außer einem Getter, der das interne Array zurückgibt (die anderen Klassen dürfen das interne Array nicht direkt verändern).

### Erwartetes Verhalten / Methoden
- **Konstruktor**  
  - Erzeugt ein leeres dynamisches Array.
  - Validiert Parameter `maxOverhead` und `growthFactor`. Ungültige Werte müssen `IllegalArgumentException` werfen (siehe Template für erlaubte Bereiche).

- **get(int index)** / **set(int index, int value)**  
  - Rein einfache Getter/Setter für die logische Position im dynamischen Array. `set` darf das Array nicht automatisch verändern (nur Wert setzen). (Genaueres siehe Template.)

- **reportUsage(Interval usage, int minSize)**  
  - `usage` beschreibt aktuell belegte, valide Positionen im Array (kann leer sein). `minSize` ist die neue Mindestanzahl an Elementen, die gespeichert werden müssen (inklusive der in `usage` bereits belegten Elemente).  
  - Verhalten:
    - Wenn `minSize` (neue Anzahl valid zu speichernder Elemente) **größer** ist als der aktuelle Platz, dann **vergrößere** das Array.
    - Wenn `minSize * maxOverhead` **kleiner** als die aktuelle Array-Größe, dann **verkleinere** das Array.
    - In beiden Fällen berechne die **neue Array-Größe** als `newSize = requiredElements * growthFactor`. (Der genaue Rundungs-/Abschlussfall richtet sich nach Template-Vorgaben.)
    - Beim Kopieren in das neue Array: **kopiere die im `usage` angegebenen Werte in der Reihenfolge**, sodass das Element an `usage.from` danach am **Anfang** (Index 0) des neuen Arrays liegt. Bei Wraparound-Intervallen (from > to) wird die Reihenfolge entsprechend vom `from` bis Ende und dann von Anfang bis `to` genommen.
    - Falls sich die Größe nicht ändert, bleibe unverändert und gib das entsprechende Interval zurück.
  - **Rückgabewert:** Das `Interval`, das angibt, wo die validen Werte **im neuen Array** liegen (z. B. `EmptyInterval` wenn keine Werte belegt sind).

- **Logging / Hilfsmethode**  
  - `DynamicArray` stellt eine Methode bereit, mit der das aktuelle Array an ein `Result` geloggt werden kann. In Folgeaufgaben wird diese Methode verwendet, daher muss sie korrekt arbeiten.

### Wichtige Hinweise
- Das interne Array darf nur über `reportUsage` neu angelegt/verschoben werden. `get`/`set` verändern die Struktur nicht (außer dem Wert).
- `reportUsage` muss generalisiert für Wraparound-Intervalle funktionieren.
- Die Implementierung ist so gehalten, dass spätere Strukturen (`Stack`, `Queue`) auf dem `DynamicArray` basieren können.

---

## Teil 2 — DynamicStack

### Aufgabe
Implementiere `DynamicStack` unter Verwendung von `DynamicArray`.

### Erwartetes Verhalten / Methoden (typisch)
- **Konstruktor(Result result)** — Result wird zum Logging verwendet (siehe Logging-Regel).
- **pushBack(int value)** — fügt ein Element hinten an.
- **int popBack()** — entfernt das letzte eingestellte Element und gibt es zurück.
- **int size()** — Anzahl der gültigen Elemente.

### Anforderungen / Verhalten
- Entfernte Elemente sollen **nicht** explizit auf `0` gesetzt werden (kein unnötiges Überschreiben — schlecht für Performance).
- Nach jeder Methode, die das Array verändert (`pushBack`, `popBack`, evtl. interne `reportUsage`-Aufrufe), muss das aktuelle Array an das `Result` geloggt werden (mithilfe der `DynamicArray`-Log-Methode).
- `pushBack` / `popBack` müssen die `reportUsage`-Semantik beachten (resize/verkleinern wie vom DynamicArray vorgegeben).
- Der Stack arbeitet logisch als `pushBack`/`popBack`-API (Ende des benutzten Bereichs).

### Beispiel (aus Aufgabenbeschreibung, growthFactor=3, maxOverhead=4)
```

Anfang: \[]
pushBack(1): \[1, 0, 0]
pushBack(2): \[1, 2, 0]
pushBack(3): \[1, 2, 3]
pushBack(4): \[1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0]
popBack(): \[1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0] (Rückgabe 4)
pushBack(5): \[1, 2, 3, 5, 0, 0, 0, 0, 0, 0, 0, 0]
popBack(): \[1, 2, 3, 5, 0, 0, 0, 0, 0, 0, 0, 0] (Rückgabe 5)
popBack(): \[1, 2, 0, 0, 0, 0] (Rückgabe 3)

```

---

## Teil 3 — RingQueue

### Aufgabe
Implementiere eine zyklische Queue (`RingQueue`) basierend auf `DynamicArray`.

### Erwartetes Verhalten / Methoden (typisch)
- **pushBack(int value)** — füge Element hinten ein. Wenn Ende des Arrays erreicht ist, aber am Anfang noch Platz vorhanden ist, verwende diesen (Wraparound).
- **int popFront()** — entferne und gib das vorderste Element zurück.
- **int size()** — Anzahl gültiger Elemente.
- **Konstruktor(Result result)** — Result für das Logging.

### Anforderungen / Verhalten
- Die Queue soll **erst** vergrößern, wenn **alle** Plätze belegt sind (also erst wenn `size == capacity`).
- Beim Pop soll das alte Element **nicht** zwangsläufig überschrieben werden (kein explizites Nulling).
- Nach jeder Methode, die das Array verändert, muss das aktuelle Array an das `Result` geloggt werden.
- Implementiere und berücksichtige die Wraparound-Logik für `usage` (bei `reportUsage`), sodass `RingQueue` korrekt mit dem `DynamicArray` interagiert.

### Beispiel (growthFactor=3, maxOverhead=4)
```

Anfang: \[]
pushBack(1): \[1, 0, 0]
pushBack(2): \[1, 2, 0]
pushBack(3): \[1, 2, 3]
pushBack(4): \[1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0]
popFront(): \[1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0] (Rückgabe 1)
popFront(): \[3, 4, 0, 0, 0, 0] (Rückgabe 2)
popFront(): \[4, 0, 0] (Rückgabe 3)
pushBack(5): \[4, 5, 0]
pushBack(6): \[4, 5, 6]
popFront(): \[4, 5, 6] (Rückgabe 4)
pushBack(7): \[7, 5, 6]
pushBack(8): \[5, 6, 7, 8, 0, 0, 0, 0, 0, 0, 0, 0]

```

---

## Teil 4 — StackyQueue

### Aufgabe
Implementiere `StackyQueue`, eine Queue, die zwei `DynamicStack`-Instanzen intern nutzt (klassische Implementierung: In-Stack und Out-Stack).

### Erwartetes Verhalten / Methoden (typisch)
- **pushBack(int value)** — schiebe Element auf `inStack`.
- **int popFront()** — falls `outStack` leer ist, transferiere alle Elemente von `inStack` zu `outStack` (durch wiederholtes pop/push) und entferne dann das oberste Element von `outStack`. Wenn `outStack` nicht leer ist, poppe direkt daraus.
- **size()** — Gesamtanzahl Elemente in beiden Stacks.

### Logging / Verhalten
- `StackyQueue` muss selbst nichts extra loggen; es reicht, dass bei den Operationen auf den `DynamicStack`-Instanzen diese ihr Array loggen (da die Stacks `Result` im Konstruktor erhalten).  
- Die Beispiele in der Aufgabenbeschreibung zeigen, dass das Loggen der Stacks separat sichtbar ist (z. B. `[] [4,3,2,1, ...]`).

### Beispiel (growthFactor=3, maxOverhead=4)
```

Anfang: \[] \[]
pushBack(1): \[1, 0, 0] \[]
pushBack(2): \[1, 2, 0] \[]
pushBack(3): \[1, 2, 3] \[]
pushBack(4): \[1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0] \[]
popFront(): \[] \[4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0] (Rückgabe 1)
popFront(): \[] \[4, 3, 0, 0, 0, 0] (Rückgabe 2)
popFront(): \[] \[4, 0, 0] (Rückgabe 3)
pushBack(5): \[5, 0, 0] \[4, 0, 0]
pushBack(6): \[5, 6, 0] \[4, 0, 0]
popFront(): \[5, 6, 0] \[] (Rückgabe 4)
popFront(): \[] \[6, 5, 0] (Rückgabe 5)

````

---

## Logging-Regeln (zusammengefasst)
- **In Teil 2–4**: Jede Methode, die das Array verändert, muss am Ende das komplette interne DynamicArray an das übergebene `Result` loggen (verwende die Hilfsmethode aus `DynamicArray`).
- `StackyQueue` loggt nichts zusätzlich — die beiden Stacks loggen bei ihren Operationen.
- **Überschreibungen oder zusätzliche Logs** sind nicht erlaubt (können zu fehlschlagenden Tests führen).

---

## Randfälle / Tests (empfohlen)
Teste die Implementierungen mit folgenden Szenarien:
- Leeres Array / leere Strukturen (Pop auf leere Struktur nach Template-Regeln behandeln).
- Viele Pushes hintereinander (vergrößern).
- Viele Pops hintereinander (Verkleinern).
- Kombinationen, die Wraparound in `RingQueue` triggern.
- `reportUsage`-Aufrufe mit:
  - `EmptyInterval`
  - `NonEmptyInterval` mit `from <= to`
  - `NonEmptyInterval` mit `from > to` (Wraparound)
  - verschiedene `minSize`-Werte (größer, gleich, kleiner als aktueller Platz)
- Prüfe, dass entfernte Werte **nicht** zwangsläufig mit `0` überschrieben werden (Performance-Anforderung).
- Logging: nach jeder Veränderungsmethode ist der genaue Array-Zustand protokolliert.
- Performance (siehe Abschnitt Performance unten).

---

## Performance-Anforderungen
- Für Performance-Tests werden üblicherweise `growthFactor = 2` und `maxOverhead = 4` verwendet.
- Test-Workload: ca. **2.500.000** Operationen (mix aus `push`, `pop`, `reportUsage`).
  - In typischen Tests: ~20% der Operationen führen zu Vergrößerung, ~15% zu Verkleinerung.
- Implementiere `reportUsage` und die Datenstrukturen effizient, um diese Last in akzeptabler Zeit zu verarbeiten.

---

## Akzeptanzkriterien
- `DynamicArray.reportUsage(...)` kopiert Werte korrekt in neuer Reihenfolge und liefert korrektes neues `Interval`.
- Dynamische Größenanpassung folgt den Regeln mit `maxOverhead` und `growthFactor`.
- `DynamicStack`, `RingQueue`, `StackyQueue` arbeiten funktional korrekt (push/pop, Wraparound, Transfer bei StackyQueue).
- Entfernte Elemente werden nicht zwingend überschrieben (kein unnötiges Clearing).
- Logging-Regeln sind eingehalten (nach jedem verändernden Aufruf wird das aktuelle Array geloggt).
- Begrenzte Anzahl an Zusatzattributen wird beachtet (siehe Template).

---

## Kompilieren / Ausführen (Beispiel)
Nutz die Template-Projektstruktur. Beispiel-Kommando:

```bash
# Kompilieren
javac -d out $(find . -name "*.java")

# Beispielprogramm (Main im Template)
java -cp out de.tu.uni.Main
````
