class SearchFilter {
  constructor() {
    this.reset();
  }

  reset() {
    this.optionMap = {};
  }

  

  addOption(key, condition, value) {
    if (!this.optionMap[key]) {
      this.optionMap[key] = [];
    }

    const index = this.optionMap[key].findIndex(
      (opt) => opt.condition === condition
    );

    if (index === -1) {
      this.optionMap[key].push({ condition, value });
    } else {
      this.optionMap[key][index] = { condition, value };
    }
  }
}