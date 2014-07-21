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

# Wie man mitentwickelt

Aufgrund von Beschränkungen durch das Studentenwerk gibt es leider kein einfaches clonen dieses Projekts, welches 
direkt funktioniert. Es wird jedoch bereits nach Lösungen für dieses Problem gesucht. Aktuelle Methode:

1. Schreibe eine Email an "Rittmeier, Florian" <Rittmeier@studentenwerk-pb.de> um einen eigenen Zugriffsschlüssel für 
die Studentenwerks-API zu erhalten
2. Erstelle eine Datei namens "mensaupb.gradle" an einem beliebigen Ort (außerhalb des Projektordners)
3. Erstelle eine Datei namens "gradle.properties" mit dem Inhalt "mensaupb.signing=<obiger pfad>"

Füge folgenden Inhalt in die mensaupb.gradle ein:


# Issue tracking, Plans for the future

See https://www.pivotaltracker.com/s/projects/1007590 - free for open source/public projects.

# Travis CI

master: [![Build Status master](https://travis-ci.org/ironjan/MensaUPB.svg?branch=master)](https://travis-ci.org/ironjan/MensaUPB)

develop: [![Build Status develop](https://travis-ci.org/ironjan/MensaUPB.svg?branch=develop)](https://travis-ci.org/ironjan/MensaUPB)

Travis CI kann von open source Projekten kostenlos genutzt werden.
