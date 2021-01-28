# LicenseSystem
License server and client system.

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
