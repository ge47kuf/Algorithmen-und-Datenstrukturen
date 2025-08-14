# Baumtraversierung

**Kurzbeschreibung**
Implementiere Algorithmen `preNext(v)` und `postNext(v)`, die für einen gegebenen Knoten `v` in einem binären Baum den *folgenden* Knoten in **Preorder** bzw. **Postorder** berechnen (oder `null`, falls kein nächster Knoten existiert). Bonus: `inNext(v)` für Inorder. Die Lösungen sollen sehr effizient sein (Tests mit Bäumen bis Höhe \~21).

---

## Annahmen / Schnittstelle

Diese README beschreibt die typische, erwartete Variante:

* Jeder Knoten besitzt Zeiger/Felder: `left`, `right`, `parent`.
  (Falls dein Template anderes vorgibt, scrolle runter zu *Ohne parent*-Abschnitt.)
* Signatur (pseudocode / Java-ähnlich):

  ```java
  class Node {
      Node left;
      Node right;
      Node parent;
      int value; // optional
  }

  Node preNext(Node v)   // Preorder-Nachfolger von v
  Node postNext(Node v)  // Postorder-Nachfolger von v
  Node inNext(Node v)    // Inorder-Nachfolger (Bonus)
  ```
* Rückgabe: der nächste Node in der jeweiligen Reihenfolge oder `null`, wenn `v` der letzte Knoten ist.

---

## Idee kurz erklärt (Intuition)

* **Preorder** (Wurzel, links, rechts): Der Nachfolger von `v` ist

  1. `v.left`, falls vorhanden;
  2. sonst `v.right`, falls vorhanden;
  3. sonst: klettere nach oben, bis du von einem Knoten `x` kommst, bei dem `x` ein linker Kind war und `x.parent.right != null` → Rückgabe `x.parent.right`; ansonsten `null`.

* **Postorder** (links, rechts, Wurzel): Nach `v` kommt

  1. Falls `v.parent == null` → `null` (v ist die letzte Wurzel).
  2. Sonst: sei `p = v.parent`.

     * Wenn `v == p.right` (oder `p.right == null`) → der nächste ist `p`.
     * Wenn `v == p.left` und `p.right != null` → der nächste ist der erste (also ganz links/oben) Knoten in `p.right`, der in Postorder **als erstes** besucht würde: starte bei `p.right` und dringe so tief wie möglich in Richtung `left` (wenn nicht vorhanden `right`) bis zu einem Blatt; das ist die nächste Postorder-Position.

* **Inorder** (links, Wurzel, rechts):

  1. Falls `v.right != null` → nächste ist das ganz linke Kind in `v.right`.
  2. Sonst: klettere nach oben, bis du ein `parent` findest, bei dem du im linken Teil warst; Rückgabe dieses Parents; sonst `null`.

Diese Algorithmen benutzen nur konstante zusätzliche Variablen und arbeiten in `O(h)` im worst-case pro Schritt (h = Höhenunterschied), amortisiert aber `O(1)` pro Schritt über eine ganze Traversierung.
<img width="755" height="347" alt="image" src="https://github.com/user-attachments/assets/a70c4795-2d19-40cc-9f8f-89705651981e" />

---

## Pseudocode / Java-Implementierung

### Preorder — `preNext`

```java
Node preNext(Node v) {
    if (v == null) return null;
    if (v.left != null) return v.left;
    if (v.right != null) return v.right;

    // Klettere nach oben, bis ein rechter Zweig verfügbar ist
    Node cur = v;
    while (cur.parent != null) {
        Node p = cur.parent;
        if (p.left == cur && p.right != null) {
            return p.right;
        }
        cur = p;
    }
    return null;
}
```

### Postorder — `postNext`

```java
Node postNext(Node v) {
    if (v == null) return null;
    Node p = v.parent;
    if (p == null) return null; // v ist Wurzel und das Ende der Postorder

    // Wenn v ist rechtes Kind oder es gibt kein rechtes Kind des Parents,
    // dann ist Parent der nächste.
    if (p.right == v || p.right == null) {
        return p;
    }

    // v ist linkes Kind und p.right existiert: gehe zur p.right und
    // finde den ersten Knoten, der in Postorder besucht wird:
    Node cur = p.right;
    while (cur.left != null || cur.right != null) {
        if (cur.left != null) cur = cur.left;
        else cur = cur.right;
    }
    return cur;
}
```

### Inorder — `inNext` (Bonus)

```java
Node inNext(Node v) {
    if (v == null) return null;
    if (v.right != null) {
        Node cur = v.right;
        while (cur.left != null) cur = cur.left;
        return cur;
    }
    // Keine rechte Teilbaum: klettere nach oben, bis wir von einem linken Kind kommen
    Node cur = v;
    while (cur.parent != null) {
        if (cur.parent.left == cur) return cur.parent;
        cur = cur.parent;
    }
    return null;
}
```

---

## Korrektheit — kurze Begründung

* **Preorder**: Preorder-Besuchsregel ist `node -> left -> right`.

  * Wenn `v.left` existiert, dann ist das direkt der nächste besuchte Knoten.
  * Wenn nicht, aber `v.right` existiert, dann als nächstes.
  * Falls beides fehlt, wurde `v` komplett bearbeitet; der Algorithmus sucht nach dem nächsten noch nicht durchlaufenen `right`-Zweig eines Vorfahren. Falls kein solcher Zweig existiert → keine weiteren Knoten.

* **Postorder**: Postorder-Besuchsregel ist `left -> right -> node`.

  * Wenn `v` das rechte Kind des Parents ist (oder Parent hat kein rechtes Kind), dann ist Parent als nächstes dran.
  * Wenn `v` das linke Kind ist und Parent hat ein rechtes Kind, dann muss vor Parent der gesamte rechte Unterbaum verarbeitet werden → also die erste Postorder-Position dieses rechten Teilbaums (dies ist der ganz linkste/oberste Leaf auf einer Abstiegregel "left if possible, else right").

* **Inorder**: Standard-Resultat (rechts-subtree oder erstes Vorfahre, wo man aus einem linken Unterbaum kommt).

Diese Regeln sind standard und korrekt für binäre Bäume mit Parent-Pointer.

---

## Randfälle

* `v == null` → Rückgabe `null`.
* Blattknoten ohne Geschwister → das Klettern nach oben muss korrekt `null` liefern, wenn kein weiterer Knoten existiert.
* Knoten mit nur einem Kind (links oder rechts) → in allen drei Varianten von oben korrekt behandelt.
* Ein-Baum (nur Wurzel): `preNext(root)`, `inNext(root)`, `postNext(root)` → alle `null`.

---

## Varianten — falls **kein** `parent`-Pointer vorhanden

Wenn die Knotenschnittstelle **keinen** Parent-Zeiger bietet, dann sind die möglichen Ansätze:

1. **Benutze den Wurzelknoten und suche iterativ die „nächste“ Position**:

   * Beispiel: Für `inNext(v)` kannst du beim Root beginnen und während des Suchens die potenzielle Nachfolger-Referenz merken (wie beim BST-Successor ohne Parent: succ = null; node=root; while(node != v) { if (v.val < node.val) { succ = node; node = node.left; } else node = node.right; } return succ; } ), aber das funktioniert nur, wenn der Baum auch ein BST ist oder du suchst nach Wertbeziehungen. Für generische Bäume hilft diese Methode nicht.
2. **Verwende externen Zustand / Stack / Iterator-Objekt**:

   * Baue einen Traversal-Iterator, der einen Stack hält und `next()` liefert — Standardlösung für fehlende parent-Zeiger. Das benötigt `O(h)` zusätzlichen Speicher, aber `next()` kann amortisiert `O(1)` sein.
3. **Kosten pro Schritt ohne parent**:

   * Einen `next(v)` rein durch Suche vom Root zu bestimmen ist im allgemeinen `O(n)` und damit für große Bäume unpraktisch.

**Fazit:** Für die geforderten großen Bäume (Höhe 20–21) ist die Variante *mit* Parent-Pointer erforderlich bzw. empfohlen — Tests mit großer Stoppuhr erwarten sehr schnelle (amortisiert konstante) Schritte.

---

## Komplexität & Performance

* Einzelne `preNext`, `postNext`, `inNext` haben worst-case Laufzeit `O(h)` (h = Höhe des Baums), da maximal bis zur Wurzel geklettert werden kann; `h` ≤ 21 in Tests → extrem klein.
* Über eine vollständige Traversierung (alle `n` Knoten) ist die amortisierte Zeit pro `next()` `O(1)` (jede Kante wird insgesamt konstant oft hoch-/runtergelaufen).
* Speicher: nur `O(1)` zusätzliche Variablen pro Aufruf (Parent-Zeiger bereits im Node).

Tests:

* Preorder-Test: Baumhöhe 21 → \~2.097.151 Knoten; vollständige Traversierung unter 1s ist machbar mit obigem O(1) amortisiert-Ansatz.
* Postorder/Inorder: Bäume Höhe 20 → \~1.048.575 Knoten; ebenfalls in <1s mit obigen Algorithmen.

---

## Beispiele (verifiziert mit der im Aufgabenblatt angegebenen Reihenfolge)

Gegeben der im Aufgabenblatt verwendeten Testbaum (die Nummern sind die Node-Werte):

* **Preorder** (Start bei Wurzel): Durchlauf gibt die erwartete aufsteigende Preorder-Reihenfolge — `preNext` liefert jeweils den nächsten Eintrag wie oben.

* **Postorder** (Start bei 4): erwartete Folge
  `4, 5, 3, 7, 8, 6, 2, 11, 12, 10, 14, 15, 13, 9, 1`
  → Mit `postNext` von 4 → 5 → 3 → ... erhältst du genau diese Sequenz.

* **Inorder** (Start bei 4): erwartete Folge
  `4, 3, 5, 2, 7, 6, 8, 1, 11, 10, 12, 9, 14, 13, 15`
  → `inNext` liefert diese Sequenz.

---

## Testempfehlungen / Unit-Tests

* **Kleine Bäume** (von Hand erzeugen) — prüfe `preNext`, `postNext`, `inNext` für viele Knoten, insbesondere:

  * Knoten mit nur linkem Kind
  * Knoten mit nur rechtem Kind
  * Blattknoten
  * Wurzelknoten
* **Mittlere Bäume**: zufällige vollständige Bäume (Höhe 5..8) — generiere Traversal-Arrays mit rekursiven Algorithmen und vergleiche sequenziell `preNext`-Aufrufe mit der rekursiven Liste.
* **Große vollständige Bäume**: Höhe 20/21 — messe Zeit für vollständige Traversierung (should be < 1s).
* **Fehlerfälle**: `preNext(null)` → `null`.

---

## Hinweise zur Implementierung

* **Achte auf Identity-Vergleiche** (`==`) beim Vergleich, ob ein Knoten das `left` oder `right` Kind eines Parents ist (nicht nur Werte).
* **Kein zusätzlicher Stack** wird benötigt, wenn `parent`-Zeiger vorhanden sind.
* Dokumentiere in Kommentaren, dass die Implementierung `parent`-Zeiger voraussetzt. Falls dein Template solche Zeiger nicht hat, implementiere zusätzlich einen Iterator mit internem Stack (nicht so performant für die großen Tests).
