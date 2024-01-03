import {ref} from "vue";
import axiosServices from "@/utils/axios";
import type {AxiosRequestConfig} from "axios";

/*type config = {
  url: string,
  data: () => void
}*/

export const useAxios = () => {
  const loading = ref(true);
  const error = ref();
  const data = ref();
  const status = ref();

  const execute = async<T>(url: string, config?: AxiosRequestConfig,
                         onSuccess?: (data: T, status?: number) => void, onRejected?: (errorCatch: any) => void) => {
    try {
      const response = await axiosServices(url, config);
      status.value = response.status;
      data.value = response.data;
      if (onSuccess) {
        onSuccess(response.data, response.status);
      }
    } catch (errorCatch) {
      console.log(errorCatch);
      error.value = true;
      if (onRejected) {
        onRejected(error);
      }
    } finally {
      error.value = false;
      loading.value = false;
    }
  }

  return {loading, error, data, status, execute}
}