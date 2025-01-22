let navigateFunction: (path: string, options?: { replace?: boolean }) => void;

export const setNavigate = (navigate: typeof navigateFunction) => {
  navigateFunction = navigate;
};

export const navigateTo = (path: string, options?: { replace?: boolean }) => {
  if (navigateFunction) {
    navigateFunction(path, options);
  } else {
    console.error("Navigate function is not set yet.");
  }
};
