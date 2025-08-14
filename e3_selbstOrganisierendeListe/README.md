# Selbstorganisierende Liste — Präsenzaufgabe P 3.3

**Kurzbeschreibung**  
Implementiere in Java eine selbstorganisierende, dynamische Liste mit drei fehlenden Methoden: `add(...)`, `findFirst(Predicate<T>)` und `removeDuplicates()`. Ziel ist es, eine Liste bereitzustellen, die sich bei Zugriffen (findFirst) selbst reorganisiert, um häufige Zugriffe zu beschleunigen. Diese Aufgabe ist eine Präsenzaufgabe (kein Notenbonus).

---

## Detaillierte Aufgabenstellung

Du erhältst ein Grundgerüst einer generischen Liste `SelfOrganizingList<T>` (Klassen-/Methodennamen können im Template leicht abweichen). Implementiere die folgenden Methoden entsprechend der in der Vorlesung besprochenen Konzepte:

### 1) `void add(...)`  
- Erzeuge einen neuen Knoten mit dem übergebenen Wert und hänge ihn **am Ende** der Liste an.  
- Laufzeitanforderung: **O(1)**. Du darfst die Klasse `SelfOrganizingList` erweitern (z. B. Felder hinzufügen), falls das nötig ist, um die Laufzeitanforderung zu erreichen.

### 2) `Optional<T> findFirst(Predicate<T> p)`  
- Durchsuche die Liste von vorne nach hinten nach dem **ersten** Knoten `n`, für den `p.test(n.data)` den Wert `true` liefert.  
- Wenn ein solcher Knoten gefunden wird:
  - Gib `Optional.of(n.data)` zurück.
  - **Verschiebe diesen Knoten an den Anfang der Liste** (Move-to-front), sodass ein nachfolgender Zugriff auf denselben Wert günstiger wird.
- Wenn kein Knoten dem Prädikat genügt:
  - Gib `Optional.empty()` zurück.
  - Die Liste darf in diesem Falle **nicht verändert** werden.
- Threading: Du kannst vereinfachend annehmen, dass nur ein Thread auf die Liste zugreift (keine Synchronisation erforderlich).

### 3) `void removeDuplicates()`  
- Entferne alle mehrfach auftretenden Werte, sodass für jeden Wert nur **das erste Auftreten** (vom Kopf aus gesehen) in der Liste verbleibt.  
- **Speicheranforderung:** zusätzlicher Speicher muss **O(1)** sein (kein zusätzlicher Speicher proportional zur Listenlänge).  
- Laufzeit: möglichst effizient; beachte aber vorrangig die Speicheranforderung.

---

## Anforderungen & Einschränkungen
- **Sprache:** Java (nutze die vorgegebenen Template-Klassen/Signaturen).  
- **Generics:** Die Liste ist generisch `SelfOrganizingList<T>`. Für Vergleich/Identifikation von Duplikaten nutze `equals()` (keine speziellen Comparatoren), wie üblich in Java.  
- **Threading:** Single-Thread-Annahme; keine Thread-Safety nötig.  
- **Erweiterungen:** Du darfst die Klasse um private Felder/Methoden erweitern (z. B. Tail-Referenz), solange öffentliche Signaturen nicht verändert werden.  
- **Fehlerbehandlung:** Robustheit bei `null`-Elementen — beachte das Template (falls `null`-Werte erlaubt sind, muss `removeDuplicates()` diese korrekt behandeln). Wenn das Template `null` ausschließt, ist keine zusätzliche `null`-Behandlung nötig.  
- **Vermeide:** kollektive Datenstrukturen mit O(n) extra Speicher (z. B. HashSet) in `removeDuplicates()` — Aufgabe verlangt O(1) extra Speicher.

---

## Erwartete Signaturen (Beispiel — nutze exakte Template-Signaturen)
```java
public class SelfOrganizingList<T> {
    // vorhandene Struktur, z. B. inner class Node { T data; Node next; }
    public void add(T value) { ... }

    public Optional<T> findFirst(Predicate<T> p) { ... }

    public void removeDuplicates() { ... }

    // ggf. weitere Helper-Methoden/Felder erlaubt (privat)
}
````

---

## Beispiele / Verhalten

Angenommen, die Liste enthält zunächst (von Kopf nach Ende):

```
[A, B, C, D, E]
```

**Beispiel 1 — add**

```java
list.add(F);
// Liste danach: [A, B, C, D, E, F]
```

`add` muss amortisiert O(1) arbeiten (z. B. durch Tail-Referenz).

**Beispiel 2 — findFirst**

* `list.findFirst(x -> x.equals("C"))` gibt `Optional.of("C")` zurück und die Liste wird zu:

```
[C, A, B, D, E]
```

(d. h. das gefundene Element wurde an den Kopf verschoben).

* Wenn das Prädikat kein Element findet, bleibt die Liste unverändert und `Optional.empty()` wird zurückgegeben.

**Beispiel 3 — removeDuplicates**

* Vorher: `[A, B, A, C, B, D]`
* Nach `removeDuplicates()` (erstes Auftreten bleibt): `[A, B, C, D]`

  * Beachte: nur O(1) zusätzlichen Speicher darf verwendet werden (kein HashSet erlaubt).

---

## Tests / Akzeptanzkriterien

Deine Implementierung sollte die folgenden Anforderungen erfüllen:

1. **Funktionalität**

   * `add` hängt korrekt am Ende an und ist O(1).
   * `findFirst` findet das erste passende Element, verschiebt es an den Kopf und gibt den Wert zurück; findet nichts → `Optional.empty()` und keine Veränderung.
   * `removeDuplicates` entfernt Duplikate, belässt nur das erste Vorkommen, und verwendet O(1) Zusatzspeicher.

2. **Korrektheit in Randfällen**

   * Leere Liste: `findFirst` → `Optional.empty()`, `removeDuplicates` → keine Fehler; `add` macht Liste nicht-leer.
   * Liste mit einem Element: alle Methoden funktionieren korrekt.
   * Alle Elemente gleich: `removeDuplicates` reduziert Liste auf ein Element.
   * `null`-Werte: Verhalte dich konsistent mit Template-Vorgabe (falls `null` erlaubt ist, gleiche Werte per `equals`/`==` behandeln; sonst `null` nicht erwartet).

3. **Performance & Speicher**

   * `add` in O(1) Zeit.
   * `removeDuplicates` in O(1) zusätzlichem Speicher.

4. **API-Konformität**

   * Verändere öffentliche Signaturen des Templates nicht.
   * Erweitere die Klasse nur intern (private Felder/Methoden sind erlaubt).

---

## Vorschläge für Tests (lokal)

Schreibe Unit-Tests (z. B. mit JUnit) für:

* `add` → überprüfe Tail-Position nach mehreren Adds und Laufzeiten für große Anzahl an Einfügungen.
* `findFirst`:

  * Treffer in der Mitte → verschoben an Kopf.
  * Treffer am Kopf → Liste bleibt inhaltlich korrekt (aber Operation darf Kopfwechsel behandeln).
  * Kein Treffer → Liste unverändert.
* `removeDuplicates`:

  * verschiedene Szenarien (keine Duplikate, einige Duplikate, alle Duplikate).
  * `null`-Werte (sofern im Template erlaubt).
* Kombinierte Abläufe: `add` → `findFirst` → `add` → `removeDuplicates`.

---

## Kompilieren / Ausführen (Beispiel)

Angenommen, dein Projekt enthält `SelfOrganizingList.java` und Test-`Main`:

```bash
# Kompilieren (Projekt-Root)
javac -d out $(find . -name "*.java")

# Beispiel starten
java -cp out com.example.Main
```

Oder lokal, ohne Paketstruktur:

```bash
javac SelfOrganizingList.java Main.java
java Main
```

(Ersetze Paket- oder Klassennamen durch die deines Templates.)

---

## Hinweise zur Abgabe

* Lege dieses `README.md` in das Root-Verzeichnis deines Übungs-Repos.
* Verändere öffentliche Methodensignaturen aus dem Template nicht.
* Dokumentiere intern kurz (als Kommentare), falls du zusätzliche private Felder/Methoden zur Erreichung von O(1)-`add` hinzugefügt hast (z. B. `tail`-Referenz).
* Nutze JUnit-Tests/Beispiel-`main()` lokal zum Testen; in der Tutoriums-Umgebung reicht die funktionierende Implementierung.

---

## Randfälle / Diskussion (kurz)

* Wie geht die Implementierung mit `null` um? Prüfe das Template; verhalte dich konsistent.
* `equals()` vs. `==` bei Duplikat-Tests: Verwende `equals()` (Standard-Java-Vergleich), es sei denn, das Template verlangt anders.
* Operationen sollten stabil sein (Reihenfolge der verbliebenen Elemente nach `removeDuplicates` entspricht dem ersten Auftreten jedes Wertes).
