const header = document.getElementById('coffeenet--header-container');
const initiallyVisible = localStorage.getItem('coffee::nav::visible') === 'true';

function handleHamburgerClick() {
  header.classList.toggle('visible');
  localStorage.setItem('coffee::nav::visible', header.classList.contains('visible'));
}

if (initiallyVisible) {
  header.classList.add('visible');
}

if (header != null) {
  header.addEventListener('click', event => {
      if (event.target.id === 'coffeenet--nav-hamburger' || event.target.parentNode.id === 'coffeenet--nav-hamburger') {
        handleHamburgerClick();
      }
    }
  );
}
