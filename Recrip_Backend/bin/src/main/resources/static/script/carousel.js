// get carousel list, buttons
const carousels = document.querySelector(".carousel-flex");
const leftButton = document.querySelector(".carousel-button-left");
const rightButton = document.querySelector(".carousel-button-right");

// get carousel single size
const size = 300;

// declare counter
let counter = 0;

// add event
leftButton.addEventListener("click", () => move(-1));
rightButton.addEventListener("click", () => move(1));

// declare function
function move(direction) {
  const carousels = document.querySelector(".carousel-flex");
  counter += direction;

  if (counter < 0 && direction == -1) counter = 4;
  if (counter > 4 && direction == 1) counter = 0;

  carousels.style.transform = `translate3d(${-300 * counter}px, 0, 0)`;
}

// call move method every 3 seconds
setInterval(() => {
  move(1);
}, 3000);
