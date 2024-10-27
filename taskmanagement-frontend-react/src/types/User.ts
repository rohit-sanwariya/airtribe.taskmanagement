
export interface IUserProfile {
    id?: number;
    firstname: string;
    lastname: string;
    email: string;
    profileImage?: string;
    createdat?: Date;
    updatedat?: Date;
    dob?: Date;
    role: ERole;
  }
  export enum TaskPriority {
    HIGH = 'HIGH',
    MEDIUM = 'MEDIUM',
    LOW = 'LOW'
  }
  export interface UserDTO {
    id: number;
    firstname: string;
    lastname: string;
    email: string;
    profileImage: string;
    createdat: string;
    updatedat: string;
    dob: string;
    role: string;
  }
 export const taskDataInitial: Partial<Task> = {
    title: '',
    description: '',
    dueDate: '',
    status: 'PENDING',
    userId: null,
    priority:TaskPriority.LOW
  };
 export enum ERole {
    // Assuming these roles are defined in your enum
    ADMIN,
    USER,
    MODERATOR,
    // Add more roles as needed
  }

  export interface Task {
    taskId: number;
    title: string;
    description: string;
    dueDate: Date | string;
    status: string;
    userId: number | null;
    userEmail: string;
    priority:TaskPriority
  }