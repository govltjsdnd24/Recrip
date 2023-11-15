// util functions
const $ = (query) => document.querySelector(query);

// modal control
function modalOn(className) {
  const targetModal = $(className);
  targetModal.style.display = "block";
}

function modalOff(className) {
  const targetModal = $(className);
  targetModal.style.display = "none";
}




// cancel btn handler
const cancelBtns = document.querySelectorAll(".modal-cancel");
cancelBtns.forEach((cancelBtn) =>
  cancelBtn.addEventListener("click", (e) => {
    e.preventDefault();
    const targetModal = e.target.parentNode.parentNode.parentNode.parentNode;
    targetModal.style.display = "none";
    console.log("modal is closed");
  })
);

// check dropdown
const dropdownImg = $(".header-users-img");
dropdownImg.addEventListener("click", (e) => {
  e.preventDefault();
  const dropdown = $(".header-dropdown");
  if (dropdown.style.display == "none") dropdown.style.display = "block";
  else if (dropdown.style.display == "block") dropdown.style.display = "none";
});
