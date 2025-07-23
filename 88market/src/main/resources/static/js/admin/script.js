document.addEventListener("DOMContentLoaded", () => {
  // Toggle submenu
  const toggleButtons = document.querySelectorAll(".toggle-submenu")

  toggleButtons.forEach((button) => {
    button.addEventListener("click", function (e) {
      e.preventDefault()

      // Get the parent li element
      const menuItem = button.closest(".has-submenu")
      if(!menuItem) return

      // Toggle the 'open' class on the parent li
      document.querySelectorAll(".has-submenu.open").forEach(openItem=>{
        if(openItem !== menuItem){
          openItem.classList.remove("open");
          const openSub=openItem.querySelector(".submenu");
          if(openSub) openSub.classList.remove("open");
        }
      });
      
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
  const menuLinks = document.querySelectorAll(".menu a")
  const currentPath = window.location.pathname
  
  menuLinks.forEach((link) => {
    const href = link.getAttribute("href")
    
    // /board/로 시작하는 모든 경로에서 /board/ 메뉴 활성화
    const isActive = href === currentPath || 
      (href.length > 1 && currentPath.startsWith(href + '/'))
    
    if (isActive) {
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
})
