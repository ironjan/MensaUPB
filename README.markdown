# Projektbeschreibung

MensaUPB ist ein Neubau von [David Maichers](https://plus.google.com/112214633916360855280) und
[Tony Lemkes](https://plus.google.com/104836562609964067147) Android App "MensaUPB".

Sie zeigt für die kommenden Tage an, was es in unserer Mensa zu essen gibt. Dazu wurde in der
ursprünglichen Version eine XML vom Studentenwerk runtergeladen und gecached, bis sie nicht mehr
verwendet werden konnte. Es wurde damals vom Studentenwerk untersagt, diese App im Play Store zu
vertreiben - daher wurde sie damals wieder entfernt.

Mittlerweile kooperiert das Studentenwerk mit Entwicklern und stellt eine Schnittstelle bereit,
über die die Speisepläne heruntergeladen werden können; daher wurde die App zu einem Großteil neu
geschrieben und ist nun endlich wieder im PlayStore ;)

# Wie man Tester wird

Diese App gibt es auch als Beta. Ihr bekommt Updates dann vor allen anderen, müsst aber damit rechnen,
dass es etwas öfter zu Fehlern kommt. Um an der Beta teilzunehmen müsst ihr folgendes tun:

 1. Tretet der [Google+ Community](https://plus.google.com/communities/100748776402713947129) oder
    der [Beta-Mailingliste](https://groups.google.com/d/forum/mensa-upb-beta) bei.
 1. Anschließend besucht ihr [diese Seite](https://play.google.com/apps/testing/de.ironjan.mensaupb)
    und werdet Tester.
 1. Sollte die App noch nicht installiert sein, könnt ihr diese aus dem [Play Store](https://play.google.com/store/apps/details?id=de.ironjan.mensaupb&ah=mtuRGeIHXZJvg6tT3jRCpoTO8fE)
    herunterladen.

Falls die App bereits installiert ist, entfällt der letzte Schritt: es gibt ein automatisches Update
auf die Beta-Version.

Ihr könnt jederzeit auf [dieser Seite](https://play.google.com/apps/testing/de.ironjan.mensaupb)
entscheiden, ob ihr Tester bleiben wollt oder nicht. Die App “wechselt” nach jeder Änderung
automatisch.

# Wie man mitentwickelt

Aufgrund von Beschränkungen durch das Studentenwerk gibt es leider kein einfaches clonen dieses Projekts, welches 
direkt funktioniert. Es wird jedoch bereits nach Lösungen für dieses Problem gesucht. Aktuelle Methode:

1. Schreibe eine Email an "Rittmeier, Florian" <Rittmeier@studentenwerk-pb.de> um eine eigene geheime URL für 
die Studentenwerks-API zu erhalten
2. Erstelle eine Datei namens "mensaupb.gradle" an einem beliebigen Ort (außerhalb des Projektordners)
3. Erstelle eine Datei namens "gradle.properties" mit dem Inhalt "mensaupb.signing=Pfad_zu_obiger_Datei"

Füge folgenden Inhalt in die mensaupb.gradle ein und ersetze GEHEIME_URL durch deine geheime URL:

```
android {
  buildTypes {
    release {
      buildConfigField "String", "STW_URL", "\"GEHEIME_URL\""
    }
    debug {
      buildConfigField "String", "STW_URL", "\"GEHEIME_URL\""
    }
  }
}
```

# Travis CI

master: [![Build Status master](https://travis-ci.org/ironjan/MensaUPB.svg?branch=master)](https://travis-ci.org/ironjan/MensaUPB)

develop: [![Build Status develop](https://travis-ci.org/ironjan/MensaUPB.svg?branch=develop)](https://travis-ci.org/ironjan/MensaUPB)

Travis CI kann von open source Projekten kostenlos genutzt werden.

# License

Copyright 2014 Jan Lippert

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
