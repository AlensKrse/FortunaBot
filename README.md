# FortunaBot
Fortuna ball telegram bot.
To update database - use maven command: process-resources -Ddb.url=jdbc:postgresql://localhost:5432/db -Ddb.user= -Dapp.user= -Ddb.pass= -Dliquibase.verbose=true -Pliqui -f pom.xml