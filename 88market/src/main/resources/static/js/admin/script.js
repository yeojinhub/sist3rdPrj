document.addEventListener("DOMContentLoaded", () => {
  // Toggle submenu
  const toggleButtons = document.querySelectorAll(".toggle-submenu")

  toggleButtons.forEach((button) => {
    button.addEventListener("click", function (e) {
      e.preventDefault()

      // Get the parent li element
      const menuItem = this.parentElement

      // Toggle the 'open' class on the parent li
      menuItem.classList.toggle("open")

      // Find the submenu within this menu item
      const submenu = menuItem.querySelector(".submenu")

      // Toggle the 'open' class on the submenu
      if (submenu) {
        submenu.classList.toggle("open")
      }
    })
  })

  // Highlight active menu item based on current page
  /*const currentPage = window.location.pathname.split("/").pop()*/
  const menuLinks = document.querySelectorAll(".menu a")

  const currentPath = window.location.pathname
  menuLinks.forEach((link) => {
    const href = link.getAttribute("href")
    if (href === currentPath) {
      link.parentElement.classList.add("active")

      const parentMenuItem = link.closest(".has-submenu")
      if (parentMenuItem) {
        parentMenuItem.classList.add("open")
        const submenu = parentMenuItem.querySelector(".submenu")
        if (submenu) {
          submenu.classList.add("open")
        }
      }
    }
  })


/*  // Add event listeners for the buttons
  const registerBtn = document.querySelector(".btn-add")
  if (registerBtn) {
    registerBtn.addEventListener("click", () => {
      alert("작성 기능이 구현될 예정입니다.")
    })
  }*/
})