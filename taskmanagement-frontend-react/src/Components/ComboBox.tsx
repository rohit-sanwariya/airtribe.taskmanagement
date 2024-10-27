 

import * as React from "react"
import { Check, ChevronsUpDown } from "lucide-react"

import { cn } from "@/lib/utils"
import { Button } from "@/Components/ui/button";
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/Components/ui/command"
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/Components/ui/popover"

 
 

interface Iprops  {
    options:Array<{value:number,label:string}>,
    placeholder:string,
    handleComboInputChange: (value: string) => void,
    owner:{id:number|undefined|null,value:string|undefined}
}

const ComboBox:React.FC<Iprops> = ({options,placeholder,handleComboInputChange,owner}:Iprops) => {
    const [open, setOpen] = React.useState(false)
    const [value, setValue] = React.useState<null|string>(null)
    React.useEffect(() => {
      if(owner?.id){
        setValue(owner?.id.toString());
      }
      return () => {
        
      };
    }, [owner]);
    return (
      <Popover open={open} onOpenChange={setOpen}>
        <PopoverTrigger asChild>
          <Button
            variant="outline"
            role="combobox"
            aria-expanded={open}
            className="w-[200px] justify-between"
          >
            {value
              ? options.find((framework) => framework.value.toString() === value.toString())?.label
              : "Select Task Owner..."}
            <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
          </Button>
        </PopoverTrigger>
        <PopoverContent className="w-[200px] p-0">
          <Command>
            <CommandInput  placeholder={placeholder} />
            <CommandList>
              <CommandEmpty>No framework found.</CommandEmpty>
              <CommandGroup>
                {options.map((framework) => (
                  <CommandItem
                    key={framework.value}
                    value={framework.value.toString()}
                    onSelect={(currentValue) => {
                      setValue(currentValue);
                      handleComboInputChange(currentValue);
                      setOpen(false)
                    }}
                  >
                    <Check
                      className={cn(
                        "mr-2 h-4 w-4",
                        value?.toString() === framework.value.toString() ? "opacity-100" : "opacity-0"
                      )}
                    />
                    {framework.label}
                  </CommandItem>
                ))}
              </CommandGroup>
            </CommandList>
          </Command>
        </PopoverContent>
      </Popover>
    )
}

export default ComboBox
 
