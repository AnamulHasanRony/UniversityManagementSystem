
document.addEventListener("DOMContentLoaded", function () {
  // This function runs when the page finishes loading

  const deptSelect = document.getElementById("department");
  const levelSelect = document.getElementById("level");
  const termSelect = document.getElementById("term");

  function fetchAndUpdate() {
    // Get selected values
    const deptCode = deptSelect.value;
    const level = levelSelect.value;
    const term = termSelect.value;

    // Only proceed if all 3 are selected
    if (deptCode && level && term) {
      // 🔄 Fetch teachers and courses from backend
      fetch(`/api/teachers?deptCode=${deptCode}`)
        .then(response => response.json())
        .then(data => {
          const teacherSelect = document.getElementById("teacher");
          teacherSelect.innerHTML = '<option value="">-- Select Teacher --</option>'; // Clear previous
          data.forEach(t => {
            const option = document.createElement("option");
            option.value = t.id;
            option.text = "(" + t.id + ") "+ t.name;
            teacherSelect.appendChild(option);
          });
        });

      fetch(`/api/courses?deptCode=${deptCode}&level=${level}&term=${term}`)
        .then(response => response.json())
        .then(data => {
          const courseSelect = document.getElementById("course");
          courseSelect.innerHTML = '<option value="">-- Select Course --</option>';

          data.forEach(c => {
            const option = document.createElement("option");
            option.value = c.code;
            option.text = c.code +" " + c.name;
            courseSelect.appendChild(option);
          });
        });
    }
  }

  // 🔁 Listen to changes
  deptSelect.addEventListener("change", fetchAndUpdate);
  levelSelect.addEventListener("change", fetchAndUpdate);
  termSelect.addEventListener("change", fetchAndUpdate);
});

