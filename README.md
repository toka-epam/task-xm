# task-xm

- Desctionption:
  Application gives brief statictics about cryptocurrencies

There is two endpints in application
1) /crypto-stats/normalized-tange - calculates normalized range
2) /crypto-stats/{crypto-name} - calculates min/max price and oldest/newest prices (avaliable names eth,btc,doge,ltc,xrp - case insensitive)


Thigns to consider:
1) There is dockerfile and docker-compose since application reads all data from files and saves into database, docker files do not work properly.
In other words, csv files are not present in docker container.
2) There are also comments in code about what can be improved.
