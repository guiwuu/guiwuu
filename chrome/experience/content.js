// Called when the user clicks on the browser action.
chrome.tabs.onUpdate.addListener(function(tab) {
  chrome.tabs.executeScript(
    null, {
        code:  "document.body.style.background='red !important'"
    }
  );
});
