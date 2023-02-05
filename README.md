# crypto-app
Simple Android MVVM architecture app to display cryptocurrency market status with the possibility to add currencies to the watchlist.

## Instructions to use:
- Intially, the app loads the list of cryptocurrencies and their situation in the global market. By
swiping right, you can see the watchlist screen, where your watchlisted cryptocurrencies will be shown.
- To add a cryptocurrency to the watchlist, click on the corresponding item. At the bottom of the app
the text will say that the item was added successfully, and you can check that by swiping to the right.
- To remove a cryptocurrency from the watchlist, a similar procedure follows. Click on the watchlisted
item to remove it from the watchlist. At the bottom of the app the text will say that the item was removed.
- The list of cryptocurrencies have an endless scroll/refresh feature, meaning that after a number of 
items shown the list will expand with extra cryptocurrencies. Note that this fetching functionality
_does not_ work in offline mode.
- The app itself can work in offline mode but no new cryptocurrencies will be loaded in the list - the
ones which got loaded will be displayed instead. Adding/removing watchlist items work as normal in offline
mode.