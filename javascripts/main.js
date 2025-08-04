document.addEventListener('DOMContentLoaded', event => {

  document.querySelectorAll('a[href^="http"]').forEach(element => {
    element.setAttribute('target', '_blank');
  });


});
