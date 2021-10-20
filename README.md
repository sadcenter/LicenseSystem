# LicenseSystem
License server and client system.

## 21.10.2021
I decided to archive this repository due this project is outdated and there is no point in creating new fixes, updates etc. cause the entire base system isn't written in the best way. I will make a new license system when I will have a little more free time.
Thanks for reading and using the project!

## Usage
```java
 Client.connect("127.0.0.1", 2138, "b449abcc-6172-11eb-ae93-0242ac130002", new LicenseCallback() {
            @Override
            public void correct() {
                System.out.println("correct");
            }

            @Override
            public void incorrect() {
                System.out.println("incorrect");
            }

            @Override
            public void disconnected(Throwable e) {
                System.out.println("cant connect!");
            }
        });
```

## Download
**[Click here](https://github.com/sadcenter/LicenseSystem/releases)**
